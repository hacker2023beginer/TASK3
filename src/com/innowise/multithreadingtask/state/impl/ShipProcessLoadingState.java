package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Ship;
import com.innowise.multithreadingtask.state.ShipProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ShipProcessLoadingState implements ShipProcessState {
    private static final Logger logger = LogManager.getLogger(ShipProcessLoadingState.class);

    @Override
    public void doProcess(Ship ship) {
        logger.info("Ship {} started loading process. Current containers: {}, capacity: {}",
                ship.getShipId(), ship.getContainers(), ship.getCapacity());
        while (ship.getContainers() < ship.getCapacity()) {
            ship.getPort().removeContainer();
            ship.setContainers(ship.getContainers() + 1);
            logger.info("Ship {} loaded one container. Now has {}/{}",
                    ship.getShipId(), ship.getContainers(), ship.getCapacity());
            logger.debug("Port containers after loading: {}", ship.getPort().getContainers().get());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Ship {} interrupted during loading", ship.getShipId());
            }
        }
        logger.info("Ship {} finished loading. Final containers: {}/{}",
                ship.getShipId(), ship.getContainers(), ship.getCapacity());
    }
}
