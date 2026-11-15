package com.parksmart.strategy;

import com.parksmart.model.ParkingSpot;

public class PricingService {

    private PricingStrategy strategy;

    public PricingService(PricingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(PricingStrategy strategy) {

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Pricing strategy cannot be null."
            );
        }

        this.strategy = strategy;
    }

    public double calculatePrice(
            ParkingSpot spot,
            int durationHours) {

        return strategy.calculatePrice(spot, durationHours);
    }

    public String getCurrentStrategy() {
        return strategy.getStrategyName();
    }
}