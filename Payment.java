package com.parksmart.factory;

public interface Payment {

    boolean pay(double amount);

    String getProviderName();
}