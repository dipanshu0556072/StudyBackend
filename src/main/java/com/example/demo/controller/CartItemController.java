package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartItemDto;
import com.example.demo.service.CartItemService;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CartItem")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/getAll")
    public List<CartItem> getAllCartItems(){
        return cartItemService.getAllCartItem();
    }

    @PostMapping("/remove/userId={userId}/cartItemId={cartItemId}")
    public String removeCartItem(@PathVariable Long userId,@PathVariable Long cartItemId){
        return cartItemService.removeCartItem(userId,cartItemId);
    }

    //remove all cart items
    @DeleteMapping("/delete/ALL/userId={userId}")
    public ResponseEntity<String> removeAllCartItem(@PathVariable  Long userId){
        try{
            cartItemService.removeCartAllItem(userId);
            return ResponseEntity.ok("cart all items removed successfully!");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("something went wrong"+e);
        }
    }


}
