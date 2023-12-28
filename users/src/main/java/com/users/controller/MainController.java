package com.users.controller;

import com.users.entity.User;
import com.users.service.RedisService;
import com.users.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @Autowired
    UsersService usersService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/login")
    public String loginForm() {
        return "loginPage";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }

    @RequestMapping("/register")
    public String addUser(Model model){
        User user = new User();
        model.addAttribute("user", user);

        return "register";
    }

    @PostMapping("/registerUser")
    public String register(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult, Model model){
        System.out.println(user);
        if (bindingResult.hasErrors()) {
            return "register";
        }
        try{
            usersService.addUser(user);
        }catch (Exception exception){
            bindingResult.reject("globalError", exception.getMessage());
            return "register";
        }
        return "redirect:/login" ;
    }

    @GetMapping("/logout")
    public String logout() {
        redisService.saveData("userId","");
        return "redirect:/login" ;
    }
}
