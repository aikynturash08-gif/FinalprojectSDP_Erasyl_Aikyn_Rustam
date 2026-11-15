package com.parksmart.decorator;

public abstract class ParkingDecorator implements ParkingOption {

    protected final ParkingOption parking;

    public ParkingDecorator(ParkingOption parking) {
        if (parking == null) {
            throw new IllegalArgumentException("Parking cannot be null.");
        }
        this.parking = parking;
    }

    @Override
    public String getDescription() {
        return parking.getDescription();
    }

    @Override
    public double getCost() {
        return parking.getCost();
    }
}