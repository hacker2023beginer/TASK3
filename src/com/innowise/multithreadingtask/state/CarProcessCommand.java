package com.innowise.multithreadingtask.state;

import com.innowise.multithreadingtask.entity.Car;

public interface CarProcessCommand {
    void doProcess(Car car);
}
