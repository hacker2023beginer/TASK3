package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.state.CarProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Car extends Thread {
    private static final Logger logger = LogManager.getLogger(Car.class);

    private int carCapacity;
    private final Port port;
    private CarProcessState state;

    public Car(int capCapacity, Port port) {
        this.carCapacity = capCapacity;
        this.port = port;
        logger.info("Car created with capacity {}", capCapacity);
    }

    public Port getPort() {
        return port;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
        logger.info("Car {} capacity set to {}", getName(), carCapacity);
    }

    @Override
    public void run() {
        logger.info("Car {} started process with capacity {}", getName(), carCapacity);
        state.doProcess(this);
        logger.info("Car {} finished process", getName());
    }

    public void setState(CarProcessState state) {
        this.state = state;
        logger.info("Car {} state set to {}", getName(), state.getClass().getSimpleName());
    }
}
