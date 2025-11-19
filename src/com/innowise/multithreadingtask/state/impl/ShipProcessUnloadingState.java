package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Ship;
import com.innowise.multithreadingtask.state.ShipProcessState;

import java.util.concurrent.TimeUnit;

public class ShipProcessUnloadingState implements ShipProcessState {
    @Override
    public void doProcess(Ship ship) {
        while (ship.getContainers() > 0) {
            ship.setContainers(ship.getContainers() - 1);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
