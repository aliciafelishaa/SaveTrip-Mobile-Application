package com.example.savetrip.model;

public class TripExchangeRate {
    public TripExchangeRate(String currency, double rate) {
        Currency = currency;
        this.rate = rate;
    }

    private String Currency;
    private double rate;

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }



}
