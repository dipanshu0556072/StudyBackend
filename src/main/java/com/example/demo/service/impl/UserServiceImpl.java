package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.UserAlreadyExistException;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepo userRepo;

    //store the new user in the database
    @Override
    public void storeUser(User user){
        if(userRepo.findUserByEmail(user.getEmail())==null){
            User newUser=new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setMobile(user.getMobile());
            newUser.setPassword(user.getPassword());
            userRepo.save(newUser);
        }else{
            throw new UserAlreadyExistException("User already registered with this email Id!");
        }
    }

    //get the list of all the users
    public List<User>getAllUserList(){
        return userRepo.findAll();
    }

    //get user by userId
    @Override
    public User userByUserId(Long userId){
      return userRepo.findById(userId).orElse(null);
    }

    //update userData by userId
    public void updateUserData(User user,Long userId){
        Optional<User>user1=userRepo.findById(userId);
        if(user1.isPresent()){
            User targetUser=user1.get();
            if(user.getFirstName()!=null){
                targetUser.setFirstName(user.getFirstName());
            }
            if(user.getLastName()!=null){
                targetUser.setLastName(user.getLastName());
            }
            if(user.getEmail()!=null){
                targetUser.setEmail(user.getEmail());
            }
            if(user.getMobile()!=null){
                targetUser.setMobile(user.getMobile());
            }
            if(user.getPassword()!=null){
                targetUser.setPassword(user.getPassword());
            }
            userRepo.save(targetUser);
        }
    }

    //delete all userData
    @Override
    public void deleteAllUsers(){
       userRepo.deleteAll();
    }

    //Delete user by userId
    @Override
    public void deleteUserById(Long userId) {
        if (userRepo.existsById(userId)) {
            userRepo.deleteById(userId);
        }
    }

}
