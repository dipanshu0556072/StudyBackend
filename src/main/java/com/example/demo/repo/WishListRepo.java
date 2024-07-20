package com.example.demo.repo;

import com.example.demo.entity.Cart;
import com.example.demo.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepo extends JpaRepository<WishList,Long> {
    WishList findByUserId(Long userId);
}
