package com.example.projetlaruchemobile;

public class SalesRequest {
    private int productLocationId;
    private int amount;

    public SalesRequest(int productLocationId, int amount) {
        this.productLocationId = productLocationId;
        this.amount = amount;
    }

    public int getProductLocationId() {
        return productLocationId;
    }

    public void setProductLocationId(int productLocationId) {
        this.productLocationId = productLocationId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
