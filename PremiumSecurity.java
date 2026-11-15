package com.parksmart.decorator;

public class PremiumSecurity extends ParkingDecorator {

    public PremiumSecurity(ParkingOption parking) {
        super(parking);
    }

    @Override
    public String getDescription() {
        return parking.getDescription() + " + Premium Security";
    }

    @Override
    public double getCost() {
        return parking.getCost() + 300;
    }
}