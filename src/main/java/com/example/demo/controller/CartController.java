package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItem;
import com.example.demo.entity.CartItemDto;
import com.example.demo.entity.WishList;
import com.example.demo.service.CartService;
import com.example.demo.service.impl.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestBody CartItemDto cartItemDto) {
        try {
            Cart cart = cartService.addToCart(userId, cartItemDto);
            return ResponseEntity.ok(cart);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //add product from wishList to cart
    @PostMapping("/addWishListToCart/userId={userId}/wishItemId={wishItemId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto,@PathVariable Long wishItemId) {
        try {
            Cart cart = cartService.addWishListToCart(userId,cartItemDto,wishItemId);
            return ResponseEntity.accepted().body("product added from cart to wishlist successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
