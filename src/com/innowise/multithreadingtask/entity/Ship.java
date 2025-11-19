package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.state.ShipProcessState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship extends Thread{
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicInteger idCounter = new AtomicInteger(0);

    private int shipId;
    private int containers;
    private int capacity;
    private ShipProcessState shipProcessState;

    public Ship(int containers, int capacity, ShipProcessState shipProcessState) {
        this.shipId = idCounter.getAndIncrement();
        this.containers = containers;
        this.capacity = capacity;
        this.shipProcessState = shipProcessState;
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
        shipProcessState.doProcess(this);
    }

    public void setShipProcessState(ShipProcessState shipProcessState) {
        this.shipProcessState = shipProcessState;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setContainers(int containers) {
        this.containers = containers;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }
}
