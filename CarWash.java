package com.parksmart.decorator;

public class CarWash extends ParkingDecorator {

    public CarWash(ParkingOption parking) {
        super(parking);
    }

    @Override
    public String getDescription() {
        return parking.getDescription() + " + Car Wash";
    }

    @Override
    public double getCost() {
        return parking.getCost() + 2000;
    }
}