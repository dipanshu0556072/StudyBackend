package com.example.demo.controller;

import com.example.demo.entity.CartItem;
import com.example.demo.entity.WishListItem;
import com.example.demo.service.WishListItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishListItem")
public class WishListItemController
{
    @Autowired
    private WishListItemService wishListItemService;

    //get all wishListItem
    @GetMapping("/getAll")
    public List<WishListItem> getAllCartItems(){
        return wishListItemService.getAllWishListProduct();
    }

    //remove wishListItems from the wishList
    @PostMapping("/remove/userId={userId}/wishListItemId={cartItemId}")
    public String removeCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
        return wishListItemService.removeWishListItem(userId,cartItemId);
    }

    //remove all wishList items
    @DeleteMapping("/delete/ALL/userId={userId}")
    public ResponseEntity<String> removeAllWishListItem(@PathVariable  Long userId){
       try{
           wishListItemService.removeWishListAllItem(userId);
           return ResponseEntity.ok("wishlist all items removed successfully!");
       }catch (Exception e){
            return ResponseEntity.badRequest().body("something went wrong"+e);
        }
    }


}
