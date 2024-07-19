package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService
{
    void storeUser(User user);
    //get user by userId
    User userByUserId(Long userId);
    //delete all userData
    void deleteAllUsers();
    //Delete user by userId
    void deleteUserById(Long userId);

}
