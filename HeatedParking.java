package com.parksmart.decorator;

public class HeatedParking extends ParkingDecorator {

    public HeatedParking(ParkingOption parking) {
        super(parking);
    }

    @Override
    public String getDescription() {
        return parking.getDescription() + " + Heated Parking";
    }

    @Override
    public double getCost() {
        return parking.getCost() + 500;
    }
}