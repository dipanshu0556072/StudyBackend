package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems = new ArrayList<>();

    @Column(name = "total_cart_amount")
    private double totalCartAmount = 0.0;

    @Column(name = "total_cart_discounted_amount")
    private double totalCartDiscountedAmount = 0.0;

    @Column(name = "total_cart_quantity")
    private int totalCartQuantity = 0;

    @Column(name = "user_id")
    private Long userId;

    public Cart() {
    }

    public Cart(List<CartItem> cartItems, double totalCartAmount, double totalCartDiscountedAmount, int totalCartQuantity, Long userId) {
        this.cartItems = cartItems;
        this.totalCartAmount = totalCartAmount;
        this.totalCartDiscountedAmount = totalCartDiscountedAmount;
        this.totalCartQuantity = totalCartQuantity;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalCartAmount() {
        return totalCartAmount;
    }

    public void setTotalCartAmount(double totalCartAmount) {
        this.totalCartAmount = totalCartAmount;
    }

    public double getTotalCartDiscountedAmount() {
        return totalCartDiscountedAmount;
    }

    public void setTotalCartDiscountedAmount(double totalCartDiscountedAmount) {
        this.totalCartDiscountedAmount = totalCartDiscountedAmount;
    }

    public int getTotalCartQuantity() {
        return totalCartQuantity;
    }

    public void setTotalCartQuantity(int totalCartQuantity) {
        this.totalCartQuantity = totalCartQuantity;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
