package com.innowise.multithreadingtask.state;

import com.innowise.multithreadingtask.entity.Car;

public interface CarProcessState {
    void doProcess(Car car);
}
