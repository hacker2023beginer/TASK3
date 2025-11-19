package com.innowise.multithreadingtask.state;

import com.innowise.multithreadingtask.entity.Ship;

public interface ShipProcessState {
    void doProcess(Ship ship);
}
