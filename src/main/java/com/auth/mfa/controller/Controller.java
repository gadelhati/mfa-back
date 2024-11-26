package com.auth.mfa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController @RequiredArgsConstructor
public class Controller {

    @GetMapping("/register")
    public ModelAndView sigNup(Model model) {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("title", "Register");
        return modelAndView;
    }
    @GetMapping("/login")
    public ModelAndView login(Model model) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("title", "Login");
        return modelAndView;
    }
    @GetMapping("/logout")
    public ModelAndView logout(Model model) {
        ModelAndView modelAndView = new ModelAndView("logout");
        modelAndView.addObject("title", "Logout");
        return modelAndView;
    }
    @GetMapping("/error")
    public ModelAndView error(Model model) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("title", "Error");
        return modelAndView;
    }
}