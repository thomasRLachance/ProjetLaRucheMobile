package com.example.projetlaruchemobile.data.model;

public class ProductModel {

    int id;
    String name;
    double price;
    int quantity;

    public ProductModel(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = 0;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void reduceQuantity() {
        this.quantity--;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
