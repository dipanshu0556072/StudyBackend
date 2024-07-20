package com.example.demo.service.impl;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.WishList;
import com.example.demo.entity.WishListItem;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.WishListItemException;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.WishListItemRepo;
import com.example.demo.repo.WishListRepo;
import com.example.demo.service.WishListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListItemServiceImpl implements WishListItemService
{
    @Autowired
    private WishListItemRepo wishListItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WishListRepo wishListRepo;

    //get all wishListItem
    @Override
    public List<WishListItem> getAllWishListProduct(){
        return wishListItemRepo.findAll();
    }

    @Override
    public String removeWishListItem(Long userId,Long wishListItemId)
    {
        //check user exist or not
        userRepo.findById(userId).orElseThrow(()->new ProductNotFoundException("User not found with given id:"+userId));


        //check the wishListItemId is exist or not
        WishListItem wishListItem=wishListItemRepo.findById(wishListItemId).orElseThrow(()->new WishListItemException("CartItem is not exist:"+wishListItemId));

        WishList wishList=wishListRepo.findByUserId(userId);
        if(wishList==null){
            throw new WishListItemException("WishList not found for user with id: " + userId);
        }

        if(!wishList.getWishListItems().contains(wishListItem)){
            throw new WishListItemException("WishListItem with id: " + wishListItemId+ " does not belong to user's wishList");
        }

        wishList.getWishListItems().remove(wishListItem);

        wishListItemRepo.delete(wishListItem);

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (WishListItem item : wishList.getWishListItems()){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * item.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*item.getQuantity());
        }
        wishList.setTotalCartQuantity(totalQuantity);
        wishList.setTotalCartDiscountedAmount(totalDiscountedAmount);
        wishList.setTotalCartAmount(totalAmount);

        wishListRepo.save(wishList);
        return "wishListItem removed successfully";
    }

    //remove all wishList items
    @Override
    public void removeWishListAllItem(Long userId){

        //check user exist or not
        userRepo.findById(userId).orElseThrow(()->new WishListItemException("WishList not exist for userId:"+userId));
        wishListItemRepo.deleteAll();


        WishList wishList=wishListRepo.findByUserId(userId);


        if(wishList==null){
            throw new WishListItemException("WishList not found for user with id: " + userId);
        }
        wishList.getWishListItems().clear();

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (WishListItem item : wishList.getWishListItems()){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * item.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*item.getQuantity());
        }
        wishList.setTotalCartQuantity(totalQuantity);
        wishList.setTotalCartDiscountedAmount(totalDiscountedAmount);
        wishList.setTotalCartAmount(totalAmount);

        wishListRepo.save(wishList);
    }



}
