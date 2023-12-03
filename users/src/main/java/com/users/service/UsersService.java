package com.users.service;

import com.users.entity.User;
import com.users.exceptions.NoEntityFoundException;
import com.users.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    @Autowired
    UsersRepository usersRepository;

    public List<User> getAll(){
        List<User> users = usersRepository.findAll();
        if(users == null){
            users = new ArrayList<>();
        }
        return users;
    }

    @Transactional
    public User getById(Integer userId){
        Optional<User> user = usersRepository.findById(userId);
        if(user.isPresent()){
            return  user.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public User getByEmailAndPassword(String email, String password){
        Optional<User> user = usersRepository.findByEmailAndUserPassword(email, password);
        if(user.isPresent()){
            return  user.get();
        }
        else throw new NoEntityFoundException("NO ENTITY FOUND");
    }

    @Transactional
    public User addUser(User user){
        return usersRepository.save(user);
    }

    @Transactional
    public User editUser(Integer userId, User user){
        Optional<User> oldUser = usersRepository.findById(userId);
        if(oldUser.isPresent()){
            usersRepository.deleteById(userId);
            usersRepository.save(user);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return user;
    }

    @Transactional
    public User deleteUser(Integer userId){
        Optional<User> oldUser = usersRepository.findById(userId);
        if(oldUser.isPresent()){
            usersRepository.deleteById(userId);
        }
        else{
            throw new NoEntityFoundException("NO ENTITY FOUND");
        }
        return oldUser.get();
    }
}
