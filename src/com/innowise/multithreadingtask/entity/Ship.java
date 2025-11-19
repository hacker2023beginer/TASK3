package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.state.ShipProcessState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Ship extends Thread{
    private final Logger log = LogManager.getLogger();
    private AtomicInteger number = new AtomicInteger(0);
    private int capacity;
    private ShipProcessState shipProcessState;

    @Override
    public void run() {
        shipProcessState.doProcess(this);
    }

    public void setShipProcessState(ShipProcessState shipProcessState) {
        this.shipProcessState = shipProcessState;
    }
}
