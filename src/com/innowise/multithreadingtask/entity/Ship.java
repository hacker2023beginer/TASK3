package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.exception.ShipThreadException;
import com.innowise.multithreadingtask.state.ShipProcessCommand;
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
    private ShipProcessCommand shipProcessCommand;

    public Ship(int containers, int capacity, ShipProcessCommand shipProcessCommand, Port port) {
        this.shipId = idCounter.getAndIncrement();
        this.containers = containers;
        this.capacity = capacity;
        this.shipProcessCommand = shipProcessCommand;
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

    public ShipProcessCommand getShipProcessState() {
        return shipProcessCommand;
    }

    @Override
    public void run() {
        try {
            logger.debug("Ship {} attempting to acquire berth", shipId);
            Berth berth = port.acquireBerth();
            logger.debug("Ship {} acquired berth {}", shipId, berth.getId());
            shipProcessCommand.doProcess(this);
            port.releaseBerth(berth);
            logger.debug("Ship {} released berth {}", shipId, berth.getId());
        } catch (ShipThreadException e) {
            logger.error("Ship {} encountered error: {}", shipId, e.getMessage(), e);
        }
    }

    public void setShipProcessCommand(ShipProcessCommand shipProcessCommand) {
        this.shipProcessCommand = shipProcessCommand;
        logger.debug("Ship {} state set to {}", shipId, shipProcessCommand.getClass().getSimpleName());
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        logger.debug("Ship {} capacity updated to {}", shipId, capacity);
    }

    public void setContainers(int containers) {
        this.containers = containers;
        logger.debug("Ship {} containers updated to {}", shipId, containers);
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
        logger.debug("Ship ID manually set to {}", shipId);
    }
}
