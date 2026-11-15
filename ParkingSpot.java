package com.parksmart.model;

public class ParkingSpot {

    public enum Status {
        AVAILABLE,
        RESERVED,
        OCCUPIED
    }

    private final String id;
    private final String type;
    private final double hourlyRate;
    private Status status;

    public ParkingSpot(String id, String type, double hourlyRate) {
        this.id = id;
        this.type = type;
        this.hourlyRate = hourlyRate;
        this.status = Status.AVAILABLE;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | %-12s | %.0f KZT/hour | %s",
                id, type, hourlyRate, status
        );
    }
}