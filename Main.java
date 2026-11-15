package com.parksmart;

import com.parksmart.facade.ParkingFacade;
import com.parksmart.factory.*;
import com.parksmart.model.*;
import com.parksmart.strategy.*;
import com.parksmart.util.DataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ParkingFacade facade = new ParkingFacade();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("======================================");
        System.out.println("             PARKSMART");
        System.out.println("        SMART PARKING SYSTEM");
        System.out.println("======================================");

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. View Parking Spots");
            System.out.println("2. Book Parking");
            System.out.println("3. View Reservations");
            System.out.println("4. Start Parking Session");
            System.out.println("5. Finish Parking Session");
            System.out.println("6. View System Information");
            System.out.println("0. Exit");

            System.out.print("\nChoose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayParkingSpots();
                    break;

                case "2":
                    bookParking();
                    break;

                case "3":
                    displayReservations();
                    break;

                case "4":
                    startParkingSession();
                    break;

                case "5":
                    finishParkingSession();
                    break;

                case "6":
                    displaySystemInformation();
                    break;

                case "0":
                    running = false;
                    System.out.println("\nThank you for using ParkSmart!");
                    break;

                default:
                    System.out.println("\nInvalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void displayParkingSpots() {

        System.out.println("\n========== PARKING SPOTS ==========");

        for (ParkingSpot spot : DataStore.getParkingSpots()) {
            System.out.println(spot);
        }
    }

    private static void bookParking() {

        System.out.println("\n========== BOOK PARKING ==========");

        Customer customer = DataStore.findCustomer("C001");

        if (customer == null) {
            System.out.println("Customer not found.");
            return;
        }

        displayParkingSpots();

        System.out.print("\nEnter parking spot ID: ");
        String spotId = scanner.nextLine();

        ParkingSpot spot = DataStore.findParkingSpot(spotId);

        if (spot == null || !spot.isAvailable()) {
            System.out.println("Parking spot is unavailable.");
            return;
        }

        int duration;

        try {
            System.out.print("Enter parking duration (hours): ");
            duration = Integer.parseInt(scanner.nextLine());

            if (duration <= 0) {
                System.out.println("Duration must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration.");
            return;
        }

        // Select pricing strategy
        System.out.println("\nSelect Pricing Strategy:");
        System.out.println("1. Hourly Pricing");
        System.out.println("2. Daily Pricing");
        System.out.println("3. Night Pricing");

        System.out.print("Choose: ");
        String pricingChoice = scanner.nextLine();

        PricingStrategy strategy;

        switch (pricingChoice) {
            case "1":
                strategy = new HourlyPricing();
                break;

            case "2":
                strategy = new DailyPricing();
                break;

            case "3":
                if (duration > 12) {
                    System.out.println("Night parking supports up to 12 hours.");
                    return;
                }
                strategy = new NightPricing();
                break;

            default:
                System.out.println("Invalid pricing option.");
                return;
        }

        // Select additional services
        List<String> services = new ArrayList<>();

        System.out.println("\n========== ADDITIONAL SERVICES ==========");
        System.out.println("1. Heated Parking (+500 KZT)");
        System.out.println("2. EV Charging (+1000 KZT/hour)");
        System.out.println("3. Premium Security (+300 KZT)");
        System.out.println("4. Car Wash (+2000 KZT)");
        System.out.println("0. No additional services");

        System.out.print("Enter service numbers separated by commas: ");
        String input = scanner.nextLine();

        if (!input.equals("0") && !input.isBlank()) {

            String[] selected = input.split(",");

            for (String item : selected) {

                switch (item.trim()) {
                    case "1":
                        services.add("heated");
                        break;

                    case "2":
                        services.add("ev");
                        break;

                    case "3":
                        services.add("security");
                        break;

                    case "4":
                        services.add("carwash");
                        break;

                    default:
                        System.out.println("Ignoring unknown service: " + item);
                }
            }
        }

        // Select payment method
        System.out.println("\n========== PAYMENT METHOD ==========");
        System.out.println("1. Kaspi QR");
        System.out.println("2. Halyk QR");
        System.out.println("3. Bank Card");

        System.out.print("Choose payment method: ");
        String paymentChoice = scanner.nextLine();

        PaymentFactory paymentFactory;

        switch (paymentChoice) {
            case "1":
                paymentFactory = new KaspiPaymentFactory();
                break;

            case "2":
                paymentFactory = new HalykPaymentFactory();
                break;

            case "3":
                paymentFactory = new CardPaymentFactory();
                break;

            default:
                System.out.println("Invalid payment method.");
                return;
        }

        // Facade handles the complete booking process
        Reservation reservation = facade.bookParking(
                customer,
                spot,
                duration,
                strategy,
                services.toArray(new String[0]),
                paymentFactory
        );

        if (reservation != null) {
            System.out.println("\nReservation ID: " + reservation.getId());
        }
    }

    private static void displayReservations() {

        System.out.println("\n========== RESERVATIONS ==========");

        if (DataStore.getReservations().isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }

        for (Reservation reservation : DataStore.getReservations()) {
            System.out.println(reservation);
            System.out.println("----------------------------------");
        }
    }

    private static void startParkingSession() {

        System.out.println("\n========== START PARKING SESSION ==========");

        displayReservations();

        System.out.print("\nEnter Reservation ID: ");
        String id = scanner.nextLine();

        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        facade.startParkingSession(reservation);
    }

    private static void finishParkingSession() {

        System.out.println("\n========== FINISH PARKING SESSION ==========");

        displayReservations();

        System.out.print("\nEnter Reservation ID: ");
        String id = scanner.nextLine();

        Reservation reservation = findReservation(id);

        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }

        facade.finishParkingSession(reservation);
    }

    private static Reservation findReservation(String id) {

        return DataStore.getReservations()
                .stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    private static void displaySystemInformation() {

        System.out.println("\n========== SYSTEM INFORMATION ==========");

        System.out.println("Total parking spots: "
                + DataStore.getParkingSpots().size());

        System.out.println("Available spots: "
                + DataStore.getParkingSpots().stream()
                .filter(ParkingSpot::isAvailable)
                .count());

        System.out.println("Registered customers: "
                + DataStore.getCustomers().size());

        System.out.println("Total reservations: "
                + DataStore.getReservations().size());

        System.out.println("Parking sessions: "
                + DataStore.getSessions().size());
    }
}