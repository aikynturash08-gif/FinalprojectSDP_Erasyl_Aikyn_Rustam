package com.parksmart.strategy;

import com.parksmart.model.ParkingSpot;

public interface PricingStrategy {

    double calculatePrice(ParkingSpot spot, int durationHours);

    String getStrategyName();
}