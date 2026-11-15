package com.parksmart.observer;

import java.util.ArrayList;
import java.util.List;

public class ParkingMonitor {

    private final List<ParkingObserver> observers = new ArrayList<>();

    public void attach(ParkingObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(ParkingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (ParkingObserver observer : observers) {
            observer.update(message);
        }
    }
}