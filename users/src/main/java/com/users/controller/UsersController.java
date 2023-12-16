package com.users.controller;

import com.users.entity.User;
import com.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    @Autowired
    UsersService usersService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(usersService.getAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserVById(@PathVariable Integer userId){
        User user = usersService.getById(userId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<User> login(@PathVariable String email, @PathVariable String password){
        User user = usersService.getByEmailAndPassword(email, password);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/addBE")
    public ResponseEntity<User> addUserBE(@RequestBody User user){
        return ResponseEntity.ok(usersService.addUser(user));
    }

    @RequestMapping("/add")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);

        return "/register";
    }

    @PostMapping("")
    public String register(@ModelAttribute("user") User user,
                                   BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "/register";
        }
        try{
            usersService.addUser(user);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "/register";
        }
        return "redirect:/login" ; //metoda din controller nu din html
    }

    @PatchMapping("/edit/{userId}")
    public ResponseEntity<User> editUser(@PathVariable Integer userId, @RequestBody User user){
        return ResponseEntity.ok(usersService.editUser(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable Integer userId){
        return ResponseEntity.ok(usersService.deleteUser(userId));
    }
}