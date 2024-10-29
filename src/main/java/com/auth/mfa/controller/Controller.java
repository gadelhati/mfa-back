package com.auth.mfa.controller;

import com.auth.mfa.persistence.model.User;
import com.auth.mfa.persistence.payload.response.DTOResponseUser;
import com.auth.mfa.persistence.repository.RepositoryUser;
import com.auth.mfa.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.awt.print.Pageable;
import java.util.Optional;

@RestController @RequiredArgsConstructor
public class Controller {

    private final ServiceUser serviceUser;
    private final RepositoryUser repositoryUser;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        if(authentication != null) {
            Optional<User> dtoRequestUser = repositoryUser.findByUsername("00038059");
            model.addAttribute("user", dtoRequestUser);
        }
        model.addAttribute("title", "Home");
        return "index";
    }
    @GetMapping("/signUp")
    public ModelAndView sigNup(Model model) {
        ModelAndView modelAndView = new ModelAndView("signUp");
        modelAndView.addObject("title", "SignUp");
        return modelAndView;
    }
    @GetMapping("/signIn")
    public ModelAndView signIn(Model model) {
        ModelAndView modelAndView = new ModelAndView("signIn");
        modelAndView.addObject("title", "SignIn");
        return modelAndView;
    }
    @GetMapping("/error")
    public String error(Model model) throws Throwable {
        model.addAttribute("title", "Login");
        model.addAttribute("error", true);
        return "login";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws Throwable {
//        DTOResponseUser dtoResponseUser = (DTOResponseUser) serviceUser.retrieve(PageRequest.of(0, 25), ""));
        model.addAttribute("title", "Login");
        model.addAttribute("error", true);
        return "login";
    }
}