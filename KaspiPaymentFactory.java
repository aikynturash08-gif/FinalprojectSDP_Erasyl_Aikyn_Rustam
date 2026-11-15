package com.parksmart.factory;

public class KaspiPaymentFactory extends PaymentFactory {

    @Override
    public Payment createPayment() {
        return new KaspiPayment();
    }
}