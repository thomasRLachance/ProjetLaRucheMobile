package com.example.projetlaruchemobile;

import java.math.BigDecimal;

public class ProductLocation {
    private int productLocationId;
    private int productId;
    private int locationId;
    private BigDecimal price;
    private boolean isActive;
    private Product product;

    public int getProductLocationId() {
        return productLocationId;
    }

    public void setProductLocationId(int productLocationId) {
        this.productLocationId = productLocationId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}