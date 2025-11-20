package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.CarProcessState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;

public class CarProcessLoadingState implements CarProcessState {
    private static final Logger logger = LogManager.getLogger();
    @Override
    public void doProcess(Car car) {
        Port port = car.getPort();
        int index = car.getCarCapacity();
        while (index > 0){
            System.out.println("Containers {} needed" + index);
            port.removeContainer();
            System.out.println("Containers {} in port right now" + port.getContainers());
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            index--;
        }
    }
}
