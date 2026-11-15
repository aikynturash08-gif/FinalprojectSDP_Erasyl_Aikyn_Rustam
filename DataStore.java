package com.parksmart.util;

import com.parksmart.model.Customer;
import com.parksmart.model.ParkingSession;
import com.parksmart.model.ParkingSpot;
import com.parksmart.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static final List<ParkingSpot> parkingSpots = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Reservation> reservations = new ArrayList<>();
    private static final List<ParkingSession> sessions = new ArrayList<>();

    static {
        initializeData();
    }

    private static void initializeData() {

        parkingSpots.add(new ParkingSpot("A01", "Standard", 500));
        parkingSpots.add(new ParkingSpot("A02", "Standard", 500));
        parkingSpots.add(new ParkingSpot("A03", "Standard", 500));

        parkingSpots.add(new ParkingSpot("B01", "Premium", 800));
        parkingSpots.add(new ParkingSpot("B02", "Premium", 800));

        parkingSpots.add(new ParkingSpot("C01", "EV Charging", 1000));
        parkingSpots.add(new ParkingSpot("C02", "EV Charging", 1000));

        customers.add(new Customer(
                "C001",
                "Test Customer",
                "+77001234567",
                "123ABC01"
        ));
    }

    public static List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public static List<Customer> getCustomers() {
        return customers;
    }

    public static List<Reservation> getReservations() {
        return reservations;
    }

    public static List<ParkingSession> getSessions() {
        return sessions;
    }

    public static ParkingSpot findParkingSpot(String id) {
        return parkingSpots.stream()
                .filter(spot -> spot.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public static Customer findCustomer(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}