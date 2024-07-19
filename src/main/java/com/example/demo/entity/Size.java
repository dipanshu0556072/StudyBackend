package com.example.demo.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Size{

    private String name;
    private int quantity;
    private String color;

    //constructor
    public Size() {
    }

    public Size(String name, int quantity, String color) {
        this.name = name;
        this.quantity = quantity;
        this.color = color;
    }
    //getter & setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
