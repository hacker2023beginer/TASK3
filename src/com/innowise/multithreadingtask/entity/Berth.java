package com.innowise.multithreadingtask.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;

public class Berth {
    private static final Logger logger = LogManager.getLogger(Berth.class);
    private static final AtomicInteger counter = new AtomicInteger(0);
    private final int id;
    private boolean occupied;

    public Berth() {
        this.id = counter.getAndIncrement();
        this.occupied = false;
        logger.info("Berth {} created. Initial state: free", id);
    }

    public int getId() {
        return id;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        occupied = true;
        logger.debug("Berth {} is now occupied", id);
    }

    public void release() {
        occupied = false;
        logger.info("Berth {} has been released", id);
    }
}
