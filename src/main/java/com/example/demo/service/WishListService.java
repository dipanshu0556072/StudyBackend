package com.example.demo.service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartItemDto;
import com.example.demo.entity.WishList;
import com.example.demo.entity.WishListItemDTO;

public interface WishListService
{
    WishList addToWishList(Long userId, WishListItemDTO wishListItemDTO);

    //add to wishList from cart
    public WishList addCartToWishlist(Long userId, CartItemDto cartItemDto, Long cartItemId);
}
