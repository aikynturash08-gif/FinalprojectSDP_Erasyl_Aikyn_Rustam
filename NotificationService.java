package com.parksmart.observer;

import com.parksmart.model.Customer;

public class NotificationService implements ParkingObserver {

    private final Customer customer;

    public NotificationService(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void update(String message) {
        System.out.println("\n========== NOTIFICATION ==========");
        System.out.println("To: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Message: " + message);
        System.out.println("==================================");
    }
}