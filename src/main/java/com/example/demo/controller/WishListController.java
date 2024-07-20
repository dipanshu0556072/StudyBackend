package com.example.demo.controller;

import com.example.demo.entity.Cart;
import com.example.demo.entity.CartItemDto;
import com.example.demo.entity.WishList;
import com.example.demo.entity.WishListItemDTO;
import com.example.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishList")
public class WishListController
{
    @Autowired
    private WishListService wishListService;


    //add product to wishList
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestParam Long userId, @RequestBody WishListItemDTO wishListItemDTO) {
        try {
            WishList wishList = wishListService.addToWishList(userId, wishListItemDTO);
            return ResponseEntity.ok(wishList);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //add product from cart to wishlist
    @PostMapping("/addCartToWishList/userId={userId}/cartItemId={cartItemId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItemDto cartItemDto,@PathVariable Long cartItemId) {
        try {
            WishList wishList = wishListService.addCartToWishlist(userId,cartItemDto,cartItemId);
            return ResponseEntity.accepted().body("product added from cart to wishlist successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
