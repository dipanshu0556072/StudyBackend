package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController
{
    @Autowired
    private UserServiceImpl userService;

    //create new user in the dataBase
    @PostMapping("/signUp")
    public ResponseEntity<String>SignUpUser(@RequestBody User user){
        userService.storeUser(user);
        return new ResponseEntity<>("user signed up successfully!", HttpStatus.CREATED);
    }

    //get the list of all users in the dataBase
    @GetMapping("/getUsersList")
    public List<User>getAllUsers(){
        return userService.getAllUserList();
    }

    //get user by userId
    @GetMapping("/userId={userId}")
    public ResponseEntity<Object>getUserByUserId(@PathVariable("userId") Long userId){
        User user=userService.userByUserId(userId);
        if(user!=null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with user id:"+userId);
        }
    }

    //update the user data by userId
    @PutMapping("/user={userId}")
    public  ResponseEntity<String>updateUser(@PathVariable("userId") Long userId,@RequestBody User user){
        if(userService.userByUserId(userId)!=null){
            userService.updateUserData(user,userId);
            return ResponseEntity.accepted().body("User data updated successfully!");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with this Id:"+userId);
        }
    }

    //delete all users
    @DeleteMapping("/deleteAllUsers")
    public ResponseEntity<String>deleteUsers(){
        userService.deleteAllUsers();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted all user from the database successfully!");
    }

    //Delete user by userId
    @DeleteMapping("/delete/userId={userId}")
    public ResponseEntity<String>deleteUserById(@PathVariable("userId") Long userId){
        if(userService.userByUserId(userId)!=null){
            userService.deleteUserById(userId);
            return ResponseEntity.accepted().body("user deleted successfully of userId:"+userId);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found for given id:"+userId);
        }
    }


}
