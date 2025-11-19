package com.innowise.multithreadingtask.state.impl;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.CarProcessState;

import java.util.concurrent.TimeUnit;


public class CarProcessUnloadingState implements CarProcessState {

    @Override
    public void doProcess(Car car) {
        Port port = car.getPort();
        int index = car.getCarCapacity();
        while (index > 0){
            port.addContainer();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            index--;
        }
    }
}
