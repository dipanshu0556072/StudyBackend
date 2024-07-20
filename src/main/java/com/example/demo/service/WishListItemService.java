package com.example.demo.service;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.WishListItem;

import java.util.List;

public interface WishListItemService
{
    //get all wishListItems
    List<WishListItem>getAllWishListProduct();

    //remove wishList item from wishList
    public String removeWishListItem(Long userId,Long wishListItemId);

    //remove all wishList item from wishList
    void removeWishListAllItem(Long userId);


}
