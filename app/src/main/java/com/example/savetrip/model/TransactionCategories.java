package com.example.savetrip.model;

public class TransactionCategories {
    private int id;
    private String name;
    private int typeId;
    private String createdAt;

    public TransactionCategories(int id, String name, int typeId, String createdAt) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


}
