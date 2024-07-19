package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItemDto;

import java.util.List;

public interface CartService
{
    //add product into the cart
    Cart addToCart(Long userId, CartItemDto cartItemDto);
}
