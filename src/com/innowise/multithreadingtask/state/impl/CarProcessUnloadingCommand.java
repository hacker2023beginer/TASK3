package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.CarProcessCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class CarProcessUnloadingCommand implements CarProcessCommand {
    private static final Logger logger = LogManager.getLogger(CarProcessUnloadingCommand.class);

    @Override
    public void doProcess(Car car) {
        Port port = car.getPort();
        int index = car.getCarCapacity();
        logger.info("Car {} started unloading process with {} containers", car.getName(), index);
        while (index > 0) {
            port.addContainer();
            logger.info("Car {} unloaded one container. Containers in port now: {}",
                    car.getName(), port.getContainers().get());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Car {} interrupted during unloading", car.getName());
            }
            index--;
        }
        logger.info("Car {} finished unloading process", car.getName());
    }
}
