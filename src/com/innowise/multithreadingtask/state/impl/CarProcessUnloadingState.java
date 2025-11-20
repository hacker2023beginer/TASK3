package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.CarProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;


public class CarProcessUnloadingState implements CarProcessState {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void doProcess(Car car) {
        logger.info("Processing Car");
        Port port = car.getPort();
        int index = car.getCarCapacity();
        while (index > 0){
            port.addContainer();
            logger.debug("Add container");
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            index--;
        }
    }
}
