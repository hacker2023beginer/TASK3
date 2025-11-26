package com.innowise.multithreadingtask.main;

import com.innowise.multithreadingtask.entity.Berth;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.entity.Ship;
import com.innowise.multithreadingtask.state.impl.ShipProcessLoadingCommand;
import com.innowise.multithreadingtask.state.impl.ShipProcessUnloadingCommand;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Port port = Port.getInstance();
        port.setPortCapacity(50);
        port.setContainers(new AtomicInteger(25));
        port.setBerthsJournal(Arrays.asList(new Berth(), new Berth(), new Berth()));
        port.setBerthSemaphore(new Semaphore(3));

        List<Ship> ships = Arrays.asList(
                new Ship(1, 2, new ShipProcessLoadingCommand(), port),
                new Ship(0, 5, new ShipProcessLoadingCommand(), port),
                new Ship(6, 6, new ShipProcessUnloadingCommand(), port),
                new Ship(1, 2, new ShipProcessLoadingCommand(), port),
                new Ship(3, 5, new ShipProcessLoadingCommand(), port),
                new Ship(6, 6, new ShipProcessUnloadingCommand(), port),
                new Ship(1, 2, new ShipProcessLoadingCommand(), port),
                new Ship(4, 5, new ShipProcessLoadingCommand(), port),
                new Ship(1, 6, new ShipProcessUnloadingCommand(), port),
                new Ship(1, 2, new ShipProcessLoadingCommand(), port),
                new Ship(0, 5, new ShipProcessLoadingCommand(), port),
                new Ship(6, 6, new ShipProcessUnloadingCommand(), port)
        );

        ships.forEach(Thread::start);

        for (Ship ship : ships) {
            ship.join();
        }
    }
}
