package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Ship;
import com.innowise.multithreadingtask.state.ShipProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class ShipProcessUnloadingState implements ShipProcessState {
    private static final Logger logger = LogManager.getLogger(ShipProcessUnloadingState.class);

    @Override
    public void doProcess(Ship ship) {
        logger.info("Ship {} started unloading process. Current containers: {}",
                ship.getShipId(), ship.getContainers());
        while (ship.getContainers() > 0) {
            ship.setContainers(ship.getContainers() - 1);
            ship.getPort().addContainer();
            logger.info("Ship {} unloaded one container. Remaining on ship: {}. Containers in port: {}",
                    ship.getShipId(), ship.getContainers(), ship.getPort().getContainers().get());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("Ship {} interrupted during unloading", ship.getShipId());
            }
        }
        logger.info("Ship {} finished unloading. Final containers on ship: {}",
                ship.getShipId(), ship.getContainers());
    }
}
