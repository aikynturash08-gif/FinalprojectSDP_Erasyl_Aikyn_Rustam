package com.parksmart.facade;

import com.parksmart.builder.ReservationBuilder;
import com.parksmart.decorator.*;
import com.parksmart.factory.PaymentFactory;
import com.parksmart.model.*;
import com.parksmart.observer.NotificationService;
import com.parksmart.observer.ParkingMonitor;
import com.parksmart.strategy.PricingStrategy;
import com.parksmart.util.DataStore;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ParkingFacade {

    private final ParkingMonitor monitor = new ParkingMonitor();

    private final Map<String, ParkingSession> activeSessions =
            new HashMap<>();

    public Reservation bookParking(
            Customer customer,
            ParkingSpot spot,
            int durationHours,
            PricingStrategy strategy,
            String[] additionalServices,
            PaymentFactory paymentFactory) {

        // 1. Subscribe customer to notifications
        monitor.attach(new NotificationService(customer));

        // 2. Create reservation using Builder
        Reservation reservation = new ReservationBuilder()
                .setCustomer(customer)
                .setParkingSpot(spot)
                .setStartTime(LocalDateTime.now().plusMinutes(1))
                .setDurationHours(durationHours)
                .build();

        // 3. Calculate price using Strategy
        double basePrice = strategy.calculatePrice(spot, durationHours);

        // 4. Add extra services using Decorator
        ParkingOption parking = new BasicParking(reservation, basePrice);

        for (String service : additionalServices) {

            switch (service.toLowerCase()) {

                case "heated":
                    parking = new HeatedParking(parking);
                    break;

                case "ev":
                    parking = new EVCharging(parking, durationHours);
                    break;

                case "security":
                    parking = new PremiumSecurity(parking);
                    break;

                case "carwash":
                    parking = new CarWash(parking);
                    break;

                default:
                    System.out.println("Unknown service: " + service);
            }
        }

        // 5. Process payment using Factory Method
        System.out.println("\n========== CHECKOUT ==========");
        System.out.println("Services: " + parking.getDescription());
        System.out.println("Total amount: " + parking.getCost() + " KZT");

        boolean paid = paymentFactory.processPayment(parking.getCost());

        if (!paid) {
            System.out.println("Payment failed.");
            return null;
        }

        // 6. Confirm reservation
        reservation.setStatus(Reservation.Status.CONFIRMED);
        spot.setStatus(ParkingSpot.Status.RESERVED);

        DataStore.getReservations().add(reservation);

        // 7. Notify customer using Observer
        monitor.notifyObservers(
                "Your reservation " + reservation.getId()
                        + " has been confirmed!"
        );

        System.out.println("\nReservation successfully created!");

        return reservation;
    }

    public void startParkingSession(Reservation reservation) {

        if (reservation == null ||
                reservation.getStatus() != Reservation.Status.CONFIRMED) {
            System.out.println("Reservation is not confirmed.");
            return;
        }

        ParkingSession session = new ParkingSession(
                "SESSION-" + reservation.getId(),
                reservation.getCustomer(),
                reservation.getParkingSpot()
        );

        activeSessions.put(reservation.getId(), session);
        DataStore.getSessions().add(session);

        monitor.notifyObservers(
                "Your parking session has started!"
        );
    }

    public void finishParkingSession(Reservation reservation) {

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        ParkingSession session = activeSessions.get(reservation.getId());

        if (session == null || !session.isActive()) {
            System.out.println("No active parking session found.");
            return;
        }

        session.finishSession();
        reservation.setStatus(Reservation.Status.COMPLETED);

        monitor.notifyObservers(
                "Your parking session has been completed!"
        );

        activeSessions.remove(reservation.getId());
    }
}