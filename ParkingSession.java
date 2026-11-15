package com.parksmart.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingSession {

    private final String id;
    private final Customer customer;
    private final ParkingSpot parkingSpot;
    private final LocalDateTime startTime;

    private LocalDateTime endTime;
    private boolean active;

    public ParkingSession(
            String id,
            Customer customer,
            ParkingSpot parkingSpot) {

        this.id = id;
        this.customer = customer;
        this.parkingSpot = parkingSpot;
        this.startTime = LocalDateTime.now();
        this.active = true;

        parkingSpot.setStatus(ParkingSpot.Status.OCCUPIED);
    }

    public void finishSession() {
        if (!active) {
            return;
        }

        endTime = LocalDateTime.now();
        active = false;

        parkingSpot.setStatus(ParkingSpot.Status.AVAILABLE);
    }

    public long getDurationHours() {
        LocalDateTime finish = active
                ? LocalDateTime.now()
                : endTime;

        long minutes = Duration.between(
                startTime, finish
        ).toMinutes();

        return Math.max(1, (minutes + 59) / 60);
    }

    public double calculateBasicCost() {
        return getDurationHours() * parkingSpot.getHourlyRate();
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

    public boolean isActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Session ID: " + id
                + "\nCustomer: " + customer.getName()
                + "\nParking spot: " + parkingSpot.getId()
                + "\nDuration: " + getDurationHours() + " hour(s)"
                + "\nEstimated cost: " + calculateBasicCost() + " KZT"
                + "\nActive: " + active;
    }
}