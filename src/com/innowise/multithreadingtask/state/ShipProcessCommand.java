package com.innowise.multithreadingtask.state;

import com.innowise.multithreadingtask.entity.Ship;

public interface ShipProcessCommand {
    void doProcess(Ship ship);
}
