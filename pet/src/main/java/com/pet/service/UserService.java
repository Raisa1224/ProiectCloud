package com.pet.service;

import com.pet.entity.Species;
import com.pet.entity.User;
import com.pet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUsers(User user){
        Optional<User> old = findById(user.getUserId());
        if(old.isPresent()){
            return user;
        }
        else{
            return userRepository.save(user);
        }
    }

    public Optional<User> findById(Integer id){
        return userRepository.findById(id);
    }
}
