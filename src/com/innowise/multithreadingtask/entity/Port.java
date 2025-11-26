package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.exception.ShipThreadException;
import com.innowise.multithreadingtask.state.impl.CarProcessLoadingCommand;
import com.innowise.multithreadingtask.state.impl.CarProcessUnloadingCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private static final Logger logger = LogManager.getLogger(Port.class);

    private Semaphore berthSemaphore;
    private List<Berth> berthsJournal;
    private final Lock lock = new ReentrantLock();
    private final double CROWDED_FACTOR = 0.9;
    private final double EMPTY_FACTOR = 0.1;
    private final double CAR_FACTOR = 0.3;
    private int portCapacity;
    private AtomicInteger containers;
    private double loadFactor;

    public void setBerthsJournal(List<Berth> berthsJournal) {
        this.berthsJournal = berthsJournal;
        logger.debug("Berths journal set with {} berths", berthsJournal.size());
    }

    public void setBerthSemaphore(Semaphore berthSemaphore) {
        this.berthSemaphore = berthSemaphore;
        logger.debug("Berth semaphore initialized with {} permits", berthSemaphore.availablePermits());
    }

    public Berth acquireBerth() throws ShipThreadException {
        try {
            berthSemaphore.acquire();
            logger.debug("Semaphore acquired by thread {}", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            logger.error("Semaphore acquire interrupted", e);
            throw new ShipThreadException("Semaphore error");
        }
        lock.lock();
        try {
            for (Berth berth : berthsJournal) {
                if (!berth.isOccupied()) {
                    berth.occupy();
                    logger.info("Berth {} occupied by thread {}", berth.getId(), Thread.currentThread().getName());
                    return berth;
                }
            }
            logger.warn("No free berth found for thread {}", Thread.currentThread().getName());
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void releaseBerth(Berth berth) {
        lock.lock();
        try {
            berth.release();
            berthSemaphore.release();
            logger.debug("Berth {} released by thread {}", berth.getId(), Thread.currentThread().getName());
        } finally {
            lock.unlock();
        }
    }

    public void setContainers(AtomicInteger containers) {
        this.containers = containers;
        logger.debug("Containers initialized with {}", containers.get());
    }

    private Port() {
        logger.debug("Port instance created");
    }

    private static class PortHolder {
        private static final Port INSTANCE = new Port();
    }

    public static Port getInstance() {
        return PortHolder.INSTANCE;
    }

    public void addContainer() {
        int newCount = containers.incrementAndGet();
        logger.debug("Container added. Total now: {}", newCount);
        lock.lock();
        try {
            double currentLoadFactor = getLoadFactor();
            if (currentLoadFactor > CROWDED_FACTOR) {
                int carCap = (int) (portCapacity * CAR_FACTOR);
                Car newCar = new Car(carCap, this);
                newCar.setCarProcessCommand(new CarProcessLoadingCommand());
                newCar.start();
                logger.debug("Crowded factor exceeded. New Car {} started for loading with capacity {}", newCar.getName(), carCap);
            }
        } finally {
            lock.unlock();
        }
    }

    public AtomicInteger getContainers() {
        return containers;
    }

    public void removeContainer() {
        int newCount = containers.decrementAndGet();
        logger.debug("Container removed. Total now: {}", newCount);
        lock.lock();
        try {
            double currentLoadFactor = getLoadFactor();
            if (currentLoadFactor < EMPTY_FACTOR) {
                int carCap = (int) (portCapacity * CAR_FACTOR);
                Car newCar = new Car(carCap, this);
                newCar.setCarProcessCommand(new CarProcessUnloadingCommand());
                newCar.start();
                logger.debug("Empty factor reached. New Car {} started for unloading with capacity {}", newCar.getName(), carCap);
            }
        } finally {
            lock.unlock();
        }
    }

    public void setPortCapacity(int portCapacity) {
        this.portCapacity = portCapacity;
        logger.debug("Port capacity set to {}", portCapacity);
    }

    public double getLoadFactor() {
        lock.lock();
        try {
            loadFactor = (double) containers.get() / (double) portCapacity;
            logger.debug("Current load factor: {}", loadFactor);
            return loadFactor;
        } finally {
            lock.unlock();
        }
    }
}
