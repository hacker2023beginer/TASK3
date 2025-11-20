package com.innowise.multithreadingtask.main;

import com.innowise.multithreadingtask.entity.Car;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.state.impl.CarProcessLoadingState;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Port port = Port.getInstance();
        port.setPortCapacity(20);
        port.setContainers(new AtomicInteger(20));
        Car car = new Car(5, port);
        car.setState(new CarProcessLoadingState());
        car.start();
        car.join();
    }
}
