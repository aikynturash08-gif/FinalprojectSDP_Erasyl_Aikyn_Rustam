package com.parksmart.model;

import java.time.LocalDateTime;

public class Reservation {

    public enum Status {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }

    private final String id;
    private final Customer customer;
    private final ParkingSpot parkingSpot;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int durationHours;

    private Status status;

    public Reservation(
            String id,
            Customer customer,
            ParkingSpot parkingSpot,
            LocalDateTime startTime,
            LocalDateTime endTime,
            int durationHours) {

        if (customer == null || parkingSpot == null) {
            throw new IllegalArgumentException(
                    "Customer and parking spot are required."
            );
        }

        if (startTime == null || endTime == null ||
                !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "Invalid reservation time."
            );
        }

        if (durationHours <= 0) {
            throw new IllegalArgumentException(
                    "Duration must be positive."
            );
        }

        this.id = id;
        this.customer = customer;
        this.parkingSpot = parkingSpot;
        this.startTime = startTime;
        this.endTime = endTime;
        this.durationHours = durationHours;
        this.status = Status.PENDING;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getDurationHours() {
        return durationHours;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double calculateBasePrice() {
        return durationHours * parkingSpot.getHourlyRate();
    }

    @Override
    public String toString() {
        return "\nReservation ID: " + id
                + "\nCustomer: " + customer.getName()
                + "\nCar: " + customer.getCarPlate()
                + "\nParking Spot: " + parkingSpot.getId()
                + "\nDuration: " + durationHours + " hours"
                + "\nStart: " + startTime
                + "\nEnd: " + endTime
                + "\nBase Price: " + calculateBasePrice() + " KZT"
                + "\nStatus: " + status;
    }
}