package com.pet.service;

import com.pet.entity.Species;
import com.pet.entity.User;
import com.pet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUsers(User user){
        return userRepository.save(user);
    }
}
