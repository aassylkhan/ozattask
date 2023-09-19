package com.example.ozattask.controller;

import com.example.ozattask.model.User;
import com.example.ozattask.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class RegistrationController {

    private UserDetailsServiceImpl accountService;

    @Autowired
    public RegistrationController(UserDetailsServiceImpl accountService){
        this.accountService = accountService;
    }

    @GetMapping()
    public String signUpPage(Model model){
        model.addAttribute("accountForm", new User());
        return "registration";
    }


    @PostMapping("/add")
    public String addAccount(@ModelAttribute("accountForm") User user){
        accountService.signup(user);
        return "redirect:/login";
    }
}