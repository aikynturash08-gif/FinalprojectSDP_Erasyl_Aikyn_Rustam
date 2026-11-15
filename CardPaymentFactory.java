package com.parksmart.factory;

public class CardPaymentFactory extends PaymentFactory {

    @Override
    public Payment createPayment() {
        return new CardPayment();
    }
}