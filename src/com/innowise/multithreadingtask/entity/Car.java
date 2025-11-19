package com.innowise.multithreadingtask.entity;

import com.innowise.multithreadingtask.state.CarProcessState;

public class Car extends Thread {
    private int carCapacity;
    private final Port port;
    private CarProcessState state;

    public Car(int capCapacity, Port port) {
        this.carCapacity = capCapacity;
        this.port = port;
    }

    public Port getPort() {
        return port;
    }

    public int getCarCapacity() {
        return carCapacity;
    }

    public void setCarCapacity(int carCapacity) {
        this.carCapacity = carCapacity;
    }

    @Override
    public void run() {
        state.doProcess(this);
    }

    public void setState(CarProcessState state) {
        this.state = state;
    }
}
