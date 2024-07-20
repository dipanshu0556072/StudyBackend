package com.example.demo.entity;

public class WishListItemDTO
{
    private Long productId;
    private String size;
    private String color;
    private int quantity;

    //constructor

    public WishListItemDTO() {
    }

    public WishListItemDTO(Long productId, String size, String color, int quantity) {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
    }

    //getter and setter


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
