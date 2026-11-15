package com.parksmart.decorator;

public class EVCharging extends ParkingDecorator {

    private final int durationHours;

    public EVCharging(ParkingOption parking, int durationHours) {
        super(parking);

        if (durationHours <= 0) {
            throw new IllegalArgumentException("Duration must be positive.");
        }

        this.durationHours = durationHours;
    }

    @Override
    public String getDescription() {
        return parking.getDescription() + " + EV Charging";
    }

    @Override
    public double getCost() {
        return parking.getCost() + (1000 * durationHours);
    }
}