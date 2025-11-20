package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.CarProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class CarProcessLoadingState implements CarProcessState {
    private static final Logger logger = LogManager.getLogger(CarProcessLoadingState.class);

    @Override
    public void doProcess(Car car) {
        Port port = car.getPort();
        int index = car.getCarCapacity();
        logger.info("Car {} started loading process with capacity {}", car.getName(), index);
        while (index > 0) {
            logger.info("Car {} still needs {} containers", car.getName(), index);
            port.removeContainer();
            logger.info("Containers in port right now: {}", port.getContainers().get());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Car {} interrupted during loading", car.getName());
            }
            index--;
        }
        logger.info("Car {} finished loading process", car.getName());
    }
}
