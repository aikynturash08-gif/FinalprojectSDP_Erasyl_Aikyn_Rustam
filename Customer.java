package com.parksmart.model;

public class Customer {

    private final String id;
    private final String name;
    private final String phone;
    private final String carPlate;

    public Customer(String id, String name, String phone, String carPlate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.carPlate = carPlate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getCarPlate() {
        return carPlate;
    }

    @Override
    public String toString() {
        return "Customer: " + name
                + " | Phone: " + phone
                + " | Car: " + carPlate;
    }
}