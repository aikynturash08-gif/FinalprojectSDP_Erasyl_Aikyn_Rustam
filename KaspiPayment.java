package com.parksmart.factory;

import java.util.UUID;

public class KaspiPayment implements Payment {

    @Override
    public boolean pay(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid payment amount.");
            return false;
        }

        System.out.println("\nConnecting to Kaspi.kz...");
        System.out.println("Generating Kaspi QR payment...");
        System.out.println("Amount: " + amount + " KZT");

        System.out.println("Processing payment...");

        String transactionId = "KAS-" +
                UUID.randomUUID().toString().substring(0, 8);

        System.out.println("\nPayment successful!");
        System.out.println("Provider: Kaspi.kz");
        System.out.println("Transaction ID: " + transactionId);

        return true;
    }

    @Override
    public String getProviderName() {
        return "Kaspi.kz";
    }
}