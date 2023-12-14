package com.adoption.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        model.addAttribute("error", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        model.addAttribute("path", request.getAttribute(
                RequestDispatcher.ERROR_REQUEST_URI));
        return "error";
    }
}

