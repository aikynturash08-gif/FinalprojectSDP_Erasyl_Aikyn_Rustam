package com.parksmart.factory;

public class HalykPaymentFactory extends PaymentFactory {

    @Override
    public Payment createPayment() {
        return new HalykPayment();
    }
}