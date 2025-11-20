package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.exception.ShipThreadException;
import com.innowise.multithreadingtask.state.ShipProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship extends Thread {
    private static final Logger logger = LogManager.getLogger(Ship.class);
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private final Port port;
    private int shipId;
    private int containers;
    private int capacity;
    private ShipProcessState shipProcessState;

    public Ship(int containers, int capacity, ShipProcessState shipProcessState, Port port) {
        this.shipId = idCounter.getAndIncrement();
        this.containers = containers;
        this.capacity = capacity;
        this.shipProcessState = shipProcessState;
        this.port = port;
        logger.info("Ship {} created with {} containers and capacity {}", shipId, containers, capacity);
    }

    public Port getPort() {
        return port;
    }

    public int getShipId() {
        return shipId;
    }

    public int getContainers() {
        return containers;
    }

    public int getCapacity() {
        return capacity;
    }

    public ShipProcessState getShipProcessState() {
        return shipProcessState;
    }

    @Override
    public void run() {
        try {
            logger.info("Ship {} attempting to acquire berth", shipId);
            Berth berth = port.acquireBerth();
            logger.info("Ship {} acquired berth {}", shipId, berth.getId());
            shipProcessState.doProcess(this);
            port.releaseBerth(berth);
            logger.info("Ship {} released berth {}", shipId, berth.getId());
        } catch (ShipThreadException e) {
            logger.error("Ship {} encountered error: {}", shipId, e.getMessage(), e);
        }
    }

    public void setShipProcessState(ShipProcessState shipProcessState) {
        this.shipProcessState = shipProcessState;
        logger.info("Ship {} state set to {}", shipId, shipProcessState.getClass().getSimpleName());
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        logger.info("Ship {} capacity updated to {}", shipId, capacity);
    }

    public void setContainers(int containers) {
        this.containers = containers;
        logger.info("Ship {} containers updated to {}", shipId, containers);
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
        logger.debug("Ship ID manually set to {}", shipId);
    }
}
