package com.backgom.backgomwineback.controller;


import com.backgom.backgomwineback.domain.LogInUser;
import com.backgom.backgomwineback.domain.UserEntity;
import com.backgom.backgomwineback.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoController {

    @Autowired
    UserService userService;

    @GetMapping("/todo")
    public String testTodo(Authentication authentication) {
        LogInUser userDetails = (LogInUser) userService.loadUserByUsername(authentication.getName());

        System.out.println(userDetails.toString());
        System.out.println(userDetails.getUsername());

        return authentication.getName();
    }



}
