package com.example.demo.service.impl;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.WishList;
import com.example.demo.entity.WishListItem;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.exception.WishListItemException;
import com.example.demo.repo.CartItemRepo;
import com.example.demo.repo.CartRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService
{
    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CartRepo cartRepo;

    //get all cart item from the cart
    @Override
    public List<CartItem> getAllCartItem(){
      return cartItemRepo.findAll();
    }


    @Override
    public String removeCartItem(Long userId,Long cartItemId){

        //check user exist or not
        userRepo.findById(userId).orElseThrow(()->new ProductNotFoundException("User not found with given id:"+userId));

        //check the cartItemId is exist or not
        CartItem cartItem=cartItemRepo.findById(cartItemId).orElseThrow(()->new ProductNotFoundException("CartItem is not exist:"+cartItemId));

        Cart cart=cartRepo.findByUserId(userId);
        if(cart==null){
            throw new ProductNotFoundException("Cart not found for user with id: " + userId);
        }

        if (!cart.getCartItems().contains(cartItem)) {
            throw new ProductNotFoundException("CartItem with id: " + cartItemId + " does not belong to userId: "+userId);
        }

        cart.getCartItems().remove(cartItem);
        cartItemRepo.delete(cartItem);

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;

        for (CartItem item : cart.getCartItems()){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * item.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*item.getQuantity());
        }
        cart.setTotalCartQuantity(totalQuantity);
        cart.setTotalCartDiscountedAmount(totalDiscountedAmount);
        cart.setTotalCartAmount(totalAmount);

        cartRepo.save(cart);

        return "CartItem removed successfully";
    }


    @Override
    public  void removeCartAllItem(Long userId){

        //check user exist or not
        userRepo.findById(userId).orElseThrow(()->new UserAlreadyExistException("Cart not exist for userId:"+userId));
        cartItemRepo.deleteAll();


        Cart cart=cartRepo.findByUserId(userId);


        if(cart==null){
            throw new WishListItemException("Cart not found for user with id: " + userId);
        }
        cart.getCartItems().clear();

        int totalQuantity = 0;
        double totalAmount = 0.0;
        double totalDiscountedAmount = 0.0;
        for (CartItem item : cart.getCartItems()){
            totalQuantity+=item.getQuantity();
            totalAmount+=(item.getProductPrice() * item.getQuantity());
            totalDiscountedAmount+=(item.getProductDiscountedPrice()*item.getQuantity());
        }
        cart.setTotalCartQuantity(totalQuantity);
        cart.setTotalCartDiscountedAmount(totalDiscountedAmount);
        cart.setTotalCartAmount(totalAmount);

       cartRepo.save(cart);
    }
}
