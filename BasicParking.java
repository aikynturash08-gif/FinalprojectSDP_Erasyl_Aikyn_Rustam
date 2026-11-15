package com.parksmart.decorator;

import com.parksmart.model.Reservation;

public class BasicParking implements ParkingOption {

    private final Reservation reservation;
    private final double basePrice;

    public BasicParking(Reservation reservation) {
        this(reservation, reservation.calculateBasePrice());
    }

    public BasicParking(Reservation reservation, double basePrice) {
        if (reservation == null) {
            throw new IllegalArgumentException("Reservation cannot be null.");
        }

        this.reservation = reservation;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription() {
        return "Basic parking at spot " +
                reservation.getParkingSpot().getId();
    }

    @Override
    public double getCost() {
        return basePrice;
    }
}