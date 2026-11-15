package com.parksmart.strategy;

import com.parksmart.model.ParkingSpot;

public class DailyPricing implements PricingStrategy {

    private static final double DAILY_RATE = 3000;

    @Override
    public double calculatePrice(ParkingSpot spot, int durationHours) {

        if (durationHours <= 0) {
            throw new IllegalArgumentException(
                    "Duration must be positive."
            );
        }

        int days = (durationHours + 23) / 24;

        return days * DAILY_RATE;
    }

    @Override
    public String getStrategyName() {
        return "Daily Pricing";
    }
}