package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name="wishListItem")
public class WishListItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "product_price")
    private double productPrice;

    @Column(name = "product_discounted_price")
    private double productDiscountedPrice;

    //constructor
    public WishListItem() {
    }

    public WishListItem(Long productId, String size, String color, int quantity, double productPrice, double productDiscountedPrice) {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.productDiscountedPrice = productDiscountedPrice;
    }

    //getter and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductDiscountedPrice() {
        return productDiscountedPrice;
    }

    public void setProductDiscountedPrice(double productDiscountedPrice) {
        this.productDiscountedPrice = productDiscountedPrice;
    }
}
