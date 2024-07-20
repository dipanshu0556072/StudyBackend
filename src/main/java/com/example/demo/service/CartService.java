package com.example.demo.service;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartItemDto;
import com.example.demo.entity.WishList;

import java.util.List;

public interface CartService
{
    //add product into the cart
    Cart addToCart(Long userId, CartItemDto cartItemDto);
    //add to cart from wishList
    public Cart addWishListToCart(Long userId, CartItemDto cartItemDto, Long wishListId);
}
