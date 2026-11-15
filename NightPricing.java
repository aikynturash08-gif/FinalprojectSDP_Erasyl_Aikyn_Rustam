package com.parksmart.strategy;

import com.parksmart.model.ParkingSpot;

public class NightPricing implements PricingStrategy {

    private static final double NIGHT_RATE = 2000;
    private static final int MAX_NIGHT_HOURS = 12;

    @Override
    public double calculatePrice(ParkingSpot spot, int durationHours) {

        if (durationHours <= 0 || durationHours > MAX_NIGHT_HOURS) {
            throw new IllegalArgumentException(
                    "Night parking must be between 1 and 12 hours."
            );
        }

        return NIGHT_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Night Pricing";
    }
}