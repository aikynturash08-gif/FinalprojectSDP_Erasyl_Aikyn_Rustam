package com.parksmart.builder;

import com.parksmart.model.Customer;
import com.parksmart.model.ParkingSpot;
import com.parksmart.model.Reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationBuilder {

    private Customer customer;
    private ParkingSpot parkingSpot;
    private LocalDateTime startTime;
    private int durationHours;

    public ReservationBuilder setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public ReservationBuilder setParkingSpot(ParkingSpot parkingSpot) {
        this.parkingSpot = parkingSpot;
        return this;
    }

    public ReservationBuilder setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public ReservationBuilder setDurationHours(int durationHours) {
        this.durationHours = durationHours;
        return this;
    }

    public Reservation build() {

        if (customer == null) {
            throw new IllegalStateException("Customer is required.");
        }

        if (parkingSpot == null) {
            throw new IllegalStateException("Parking spot is required.");
        }

        if (!parkingSpot.isAvailable()) {
            throw new IllegalStateException("Parking spot is not available.");
        }

        if (startTime == null || startTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Invalid reservation start time.");
        }

        if (durationHours <= 0) {
            throw new IllegalStateException("Duration must be positive.");
        }

        LocalDateTime endTime = startTime.plusHours(durationHours);

        return new Reservation(
                "RES-" + UUID.randomUUID().toString().substring(0, 8),
                customer,
                parkingSpot,
                startTime,
                endTime,
                durationHours
        );
    }
}