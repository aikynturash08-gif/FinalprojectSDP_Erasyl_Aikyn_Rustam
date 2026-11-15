package com.parksmart.strategy;

import com.parksmart.model.ParkingSpot;

public class HourlyPricing implements PricingStrategy {

    @Override
    public double calculatePrice(ParkingSpot spot, int durationHours) {

        if (durationHours <= 0) {
            throw new IllegalArgumentException(
                    "Duration must be positive."
            );
        }

        return spot.getHourlyRate() * durationHours;
    }

    @Override
    public String getStrategyName() {
        return "Hourly Pricing";
    }
}