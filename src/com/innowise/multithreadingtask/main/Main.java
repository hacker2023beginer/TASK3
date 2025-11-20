package com.innowise.multithreadingtask.main;

import com.innowise.multithreadingtask.entity.Berth;
import com.innowise.multithreadingtask.entity.Port;
import com.innowise.multithreadingtask.entity.Ship;
import com.innowise.multithreadingtask.state.impl.ShipProcessLoadingState;
import com.innowise.multithreadingtask.state.impl.ShipProcessUnloadingState;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Port port = Port.getInstance();
        port.setPortCapacity(20);
        port.setContainers(new AtomicInteger(20));
        port.setBerthsJournal(Arrays.asList(new Berth(), new Berth(), new Berth()));
        port.setBerthSemaphore(new Semaphore(3));

        List<Ship> ships = Arrays.asList(
                new Ship(1, 2, new ShipProcessLoadingState(), port),
                new Ship(0, 5, new ShipProcessLoadingState(), port),
                new Ship(6, 6, new ShipProcessUnloadingState(), port),
                new Ship(1, 2, new ShipProcessLoadingState(), port),
                new Ship(3, 5, new ShipProcessLoadingState(), port),
                new Ship(6, 6, new ShipProcessUnloadingState(), port),
                new Ship(1, 2, new ShipProcessLoadingState(), port),
                new Ship(4, 5, new ShipProcessLoadingState(), port),
                new Ship(1, 6, new ShipProcessUnloadingState(), port),
                new Ship(1, 2, new ShipProcessLoadingState(), port),
                new Ship(0, 5, new ShipProcessLoadingState(), port),
                new Ship(6, 6, new ShipProcessUnloadingState(), port)
        );

        ships.forEach(Thread::start);

        for (Ship ship : ships) {
            ship.join();
        }

        System.out.println("Все корабли завершили работу");
    }
}
