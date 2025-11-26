package com.example.savetrip.model;

public class Trip {
    private int tripId;
    private int userId;
    private String tripName;
    private String destinationCountry;
    private String startDate;
    private String endDate;
    private double initialBudget;
    private String baseCurrency;
    private double outcomeTotalTransaction;
    private String note;
    private String createdAt;

    public Trip() {}
    public Trip(int userId, String tripName, String destinationCountry, String startDate, String endDate, double initialBudget, String baseCurrency, String note) {
        this.userId = userId;
        this.tripName = tripName;
        this.destinationCountry = destinationCountry;
        this.startDate = startDate;
        this.endDate = endDate;
        this.initialBudget = initialBudget;
        this.baseCurrency = baseCurrency;
        this.note = note;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(double initialBudget) {
        this.initialBudget = initialBudget;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public double getOutcomeTotalTransaction() {
        return outcomeTotalTransaction;
    }

    public void setOutcomeTotalTransaction(double outcomeTotalTransaction) {
        this.outcomeTotalTransaction = outcomeTotalTransaction;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
