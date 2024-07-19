package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repo.CartRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepository;

    @Autowired
    private ProductRepo productRepository;

    @Override
    @Transactional
    public Cart addToCart(Long userId, CartItemDto cartItemDto) {
        // Fetch the product from the database
        Optional<Product> productOptional = productRepository.findById(cartItemDto.getProductId());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found!");
        }

        Product product = productOptional.get();

//        // Check if product details (size, color) match
//        if (!product.getSize().equals(cartItemDto.getSize()) || !product.equals(cartItemDto.getColor())) {
//            throw new RuntimeException("Product details (size, color) do not match!");
//        }
//
//        // Check if sufficient quantity is available
//        if (product.getQuantity() < cartItemDto.getQuantity()) {
//            throw new RuntimeException("Insufficient stock!");
//        }

        // Reduce product stock quantity
        product.setQuantity(product.getQuantity() - cartItemDto.getQuantity());
        productRepository.save(product);

        // Create CartItem
        CartItem cartItem = new CartItem();
        cartItem.setProductId(product.getId());
        cartItem.setSize(cartItemDto.getSize());
        cartItem.setColor(cartItemDto.getColor());
        cartItem.setQuantity(cartItemDto.getQuantity());
        cartItem.setProductPrice(Double.parseDouble(product.getPrice()));
        // Calculate discounted price if applicable
        // cartItem.setProductDiscountedPrice(...);

        // Update Cart
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }

        cart.getCartItems().add(cartItem);
        cart.setTotalCartAmount(cart.getTotalCartAmount() + (cartItem.getProductPrice() * cartItem.getQuantity()));
        // Update discounted amount and total quantity accordingly
        // cart.setTotalCartDiscountedAmount(...);
        // cart.setTotalCartQuantity(...);

        return cartRepository.save(cart);
    }
}