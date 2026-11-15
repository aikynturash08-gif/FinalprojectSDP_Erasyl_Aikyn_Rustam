package com.parksmart.factory;

public abstract class PaymentFactory {

    public abstract Payment createPayment();

    public boolean processPayment(double amount) {

        Payment payment = createPayment();

        System.out.println("\nPayment method: "
                + payment.getProviderName());

        return payment.pay(amount);
    }
}