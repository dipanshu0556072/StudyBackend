package com.example.demo.service;

import com.example.demo.entity.CartItem;

import java.util.List;

public interface CartItemService
{
    //get all cartItems
    List<CartItem>getAllCartItem();

    //remove cartItem from cart
    String removeCartItem(Long userId,Long cartItemId);

    //remove all cart item from wishList
    void removeCartAllItem(Long userId);
}
