package com.example.demo.repo;

import com.example.demo.entity.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListItemRepo extends JpaRepository<WishListItem,Long> {
}
