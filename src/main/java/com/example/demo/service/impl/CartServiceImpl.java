package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.WishListItemException;
import com.example.demo.repo.*;
import com.example.demo.service.CartService;
import com.example.demo.service.WishListItemService;
import com.example.demo.service.WishListService;
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
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WishListItemRepo wishListItemRepo;

    @Autowired
    private WishListRepo wishListRepo;

    @Autowired
    private WishListItemService wishListItemService;

    //add product in the cart
    public Cart addToCart(Long userId, CartItemDto cartItemDto) {

        //check userId is exist or not
        if(!userRepo.findById(userId).isPresent()){
            throw new UserAlreadyExistException("User not found with given id:"+userId);
        }

        // Check if the product exists with the given productId
        Product product = productRepo.findById(cartItemDto.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product not found in database with given id: " + cartItemDto.getProductId()));

        // Check if the product is available with given size, color, and quantity
        boolean isProductFound = false;
        for (Size size : product.getSize()) {
            if (size.getName().equalsIgnoreCase(cartItemDto.getSize()) &&
                    size.getColor().equalsIgnoreCase(cartItemDto.getColor()) &&
                    cartItemDto.getQuantity()<= size.getQuantity() ) {
                isProductFound = true;
                break;
            }
        }
        if (!isProductFound) {
            throw new ProductNotFoundException("Product not found in database with given size, color, quantity");
        }

        // Update the stock quantity in the database
        for (Size size : product.getSize()) {
            if (size.getName().equalsIgnoreCase(cartItemDto.getSize()) &&
                    size.getColor().equalsIgnoreCase(cartItemDto.getColor()) &&
                    size.getQuantity() >= cartItemDto.getQuantity()) {
                size.setQuantity(size.getQuantity() - cartItemDto.getQuantity());
                break;
            }
        }
        productRepo.save(product);

        // Store in the cartItem table
        CartItem cartItem=new CartItem();
        cartItem.setProductId(cartItemDto.getProductId());
        cartItem.setColor(cartItemDto.getColor());
        cartItem.setSize(cartItemDto.getSize());
        cartItem.setProductDiscountedPrice(Double.parseDouble(product.getDiscountedPrice()));
        cartItem.setProductPrice(Double.parseDouble(product.getPrice()));
        cartItem.setQuantity(cartItemDto.getQuantity());

        Cart cart=cartRepo.findByUserId(userId);
        if (cart==null){
            cart=new Cart();
            cart.setUserId(userId);
            cart.setCartItems(new ArrayList<>());
        }
        List<CartItem>cartItems=cart.getCartItems();
        cartItems.add(cartItem);

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (CartItem item : cartItems){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * cartItemDto.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*cartItemDto.getQuantity());
        }
        cart.setTotalCartQuantity(totalQuantity);
        cart.setTotalCartDiscountedAmount(totalDiscountedAmount);
        cart.setTotalCartAmount(totalAmount);
        // Save the cart in the database
        return cartRepo.save(cart);
    }


    //add to cart from wishList
    @Override
    public Cart addWishListToCart(Long userId, CartItemDto cartItemDto, Long wishListId){
        // check the userId exist in the database in the database
        userRepo.findById(userId).orElseThrow(()->new UserAlreadyExistException("User not found with given id:"+userId));

        //check the product is exist in the database with the given id
        Product product=productRepo.findById(cartItemDto.getProductId()).orElseThrow(()->new WishListItemException("Product not found in database with given id:"+cartItemDto.getProductId()));

        //now check the cartItemId exist in the CartItem Table or not
        WishListItem wishListItem=wishListItemRepo.findById(wishListId).orElseThrow(()-> new WishListItemException("cartItemId="+wishListId+" not exist in the database"));

        //now store the cartItem data in the database
        CartItem cartItem=new CartItem();
        cartItem.setProductPrice(wishListItem.getProductPrice());
        cartItem.setProductDiscountedPrice(wishListItem.getProductDiscountedPrice());
        cartItem.setColor(wishListItem.getColor());
        cartItem.setSize(wishListItem.getSize());
        cartItem.setQuantity(wishListItem.getQuantity());
        cartItem.setProductId(wishListItem.getProductId());

        //now check, is wishlist exist in wishlist table with userId, if yes then restore that and update else create new for that userId
        Cart cart=cartRepo.findByUserId(userId);
        if(cart==null){
            cart=new Cart();
            cart.setUserId(userId);
            cart.setCartItems(new ArrayList<>());
        }
        List<CartItem>cartItems=cart.getCartItems();
        cartItems.add(cartItem);


        //now calculate the total summary for wishList

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (CartItem item : cartItems){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice());
            totalDiscountedAmount+=(item.getProductDiscountedPrice());
        }
        cart.setTotalCartQuantity(totalQuantity);
        cart.setTotalCartDiscountedAmount(totalDiscountedAmount);
        cart.setTotalCartAmount(totalAmount);

        // now remove the product from the cart
        wishListItemService.removeWishListItem(userId,wishListId);

        //now save it in the table of Wishlist
        return cartRepo.save(cart);

    }



}

