package com.users.controller;

import com.users.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {


    @GetMapping("/login")
    public String loginForm() {
        return "loginPage";
    }

    @GetMapping("/access_denied")
    public String accessDeniedPage(){ return "accessDenied"; }

}
