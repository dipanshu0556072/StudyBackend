package com.example.demo.repo;

import com.example.demo.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem,Long>
{

}
