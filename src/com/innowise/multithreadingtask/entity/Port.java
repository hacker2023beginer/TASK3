package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.state.impl.CarProcessLoadingState;
import com.innowise.multithreadingtask.state.impl.CarProcessUnloadingState;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private List<Berth> birhtsJournal = new ArrayList<>();
    private final Lock lock = new ReentrantLock();
    private final double CROWDED_FACTOR = 0.9;
    private final double EMPTY_FACTOR = 0.1;
    private final double CAR_FACTOR = 0.3;
    private int portCapacity;
    private AtomicInteger containers;
    private double loadFactor;

    public void setBirhts(List<Berth> birhtsJournal) {
        this.birhtsJournal = birhtsJournal;
    }

    public void setContainers(AtomicInteger containers) {
        this.containers = containers;
    }

    private Port() {
    }

    private static class PortHolder {
        private static final Port INSTANCE = new Port();
    }

    public static Port getInstance() {
        return PortHolder.INSTANCE;
    }

    public void addContainer() {
        containers.incrementAndGet();
        double currentLoadFactor = getLoadFactor();
        lock.lock();
        if (currentLoadFactor > CROWDED_FACTOR) {
            int carCap = (int) (portCapacity * CAR_FACTOR);
            Car newCar = new Car(carCap, this);
            newCar.setState(new CarProcessLoadingState());
            newCar.start();
        }
        lock.unlock();
    }

    public AtomicInteger getContainers() {
        return containers;
    }

    public void removeContainer() {
        containers.decrementAndGet();
        double currentLoadFactor = getLoadFactor();
        lock.lock();
        if (currentLoadFactor < EMPTY_FACTOR) {
            int carCap = (int) (portCapacity * CAR_FACTOR);
            Car newCar = new Car(carCap, this);
            newCar.setState(new CarProcessUnloadingState());
            newCar.start();
        }
        lock.unlock();
    }

    public void setPortCapacity(int portCapacity) {
        this.portCapacity = portCapacity;
    }

    public double getLoadFactor() {
        try {
            lock.lock();
            loadFactor = (double) containers.get() / (double) portCapacity;
            return loadFactor;
        } finally {
            lock.unlock();
        }
    }
}
