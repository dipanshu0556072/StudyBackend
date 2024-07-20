package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.WishListItemException;
import com.example.demo.repo.*;
import com.example.demo.service.CartItemService;
import com.example.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class WishListServiceImpl implements WishListService
{
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private WishListRepo wishListRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private CartItemService cartItemService;

    @Override
    public  WishList addToWishList(Long userId, WishListItemDTO wishListItemDTO){


        //check user exist in the database with given id
        userRepo.findById(userId).orElseThrow(()->new UserAlreadyExistException("User not found with given id:"+userId));

        //check the product is exist in the database with the given id
        productRepo.findById(wishListItemDTO.getProductId()).orElseThrow(()->new WishListItemException("Product not found in database with given id:"+wishListItemDTO.getProductId()));

        Product product=productRepo.findById(userId).get();

        // Check if the product is available with given size, color, and quantity
        boolean isProductFound=false;
        for(Size size:product.getSize()){
            if (size.getName().equalsIgnoreCase(wishListItemDTO.getSize()) &&
                    size.getColor().equalsIgnoreCase(wishListItemDTO.getColor()) &&
                    wishListItemDTO.getQuantity()<= size.getQuantity() ) {
                isProductFound = true;
                break;
            }
        }
        if(!isProductFound){
            throw new ProductNotFoundException("Product not found in database with given size, color, quantity");
        }

        // Store in the wishListItem table
        WishListItem wishListItem = new WishListItem();
        wishListItem.setProductId(wishListItemDTO.getProductId());
        wishListItem.setColor(wishListItemDTO.getColor());
        wishListItem.setSize(wishListItemDTO.getSize());
        wishListItem.setProductDiscountedPrice(Double.parseDouble(product.getDiscountedPrice()));
        wishListItem.setProductPrice(Double.parseDouble(product.getPrice()));
        wishListItem.setQuantity(wishListItemDTO.getQuantity());


        //store in WishList
        WishList wishList=wishListRepo.findByUserId(userId);
        if(wishList==null){
            wishList=new WishList();
            wishList.setUserId(userId);
            wishList.setWishListItems(new ArrayList<>());
        }
        List<WishListItem>wishListItems=wishList.getWishListItems();
        wishListItems.add(wishListItem);


        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (WishListItem item : wishListItems){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * wishListItemDTO.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*wishListItemDTO.getQuantity());
        }
        wishList.setTotalCartQuantity(totalQuantity);
        wishList.setTotalCartDiscountedAmount(totalDiscountedAmount);
        wishList.setTotalCartAmount(totalAmount);
        // Save the cart in the database
        return wishListRepo.save(wishList);
    }

    //add to wishList from cart
    public WishList addCartToWishlist(Long userId, CartItemDto cartItemDto, Long cartItemId){

        // check the userId exist in the database in the database
        userRepo.findById(userId).orElseThrow(()->new UserAlreadyExistException("User not found with given id:"+userId));

        //check the product is exist in the database with the given id
        Product product=productRepo.findById(cartItemDto.getProductId()).orElseThrow(()->new WishListItemException("Product not found in database with given id:"+cartItemDto.getProductId()));

        //now check the cartItemId exist in the CartItem Table or not
        CartItem cartItem=cartItemRepo.findById(cartItemId).orElseThrow(()-> new WishListItemException("cartItemId="+cartItemId+" not exist in the database"));

        //now store the cartItem data in the database
        WishListItem wishListItem=new WishListItem();
        wishListItem.setProductPrice(cartItem.getProductPrice());
        wishListItem.setProductDiscountedPrice(cartItem.getProductDiscountedPrice());
        wishListItem.setColor(cartItem.getColor());
        wishListItem.setSize(cartItem.getSize());
        wishListItem.setQuantity(cartItem.getQuantity());
        wishListItem.setProductId(cartItem.getProductId());

        //now check, is wishlist exist in wishlist table with userId, if yes then restore that and update else create new for that userId
        WishList wishList=wishListRepo.findByUserId(userId);
        if(wishList==null){
            wishList=new WishList();
            wishList.setUserId(userId);
            wishList.setWishListItems(new ArrayList<>());
        }
        List<WishListItem>wishListItems=wishList.getWishListItems();
        wishListItems.add(wishListItem);


        //now calculate the total summary for wishList

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (WishListItem item : wishListItems){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice());
            totalDiscountedAmount+=(item.getProductDiscountedPrice());
        }
        wishList.setTotalCartQuantity(totalQuantity);
        wishList.setTotalCartDiscountedAmount(totalDiscountedAmount);
        wishList.setTotalCartAmount(totalAmount);

        // now remove the product from the cart
        cartItemService.removeCartItem(userId,cartItemId);


        //now save it in the table of Wishlist
        return wishListRepo.save(wishList);
    }
}
