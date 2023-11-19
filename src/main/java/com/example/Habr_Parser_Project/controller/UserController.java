package com.example.Habr_Parser_Project.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("secured")
@Log4j2
public class UserController {
    @GetMapping("/user")
    public String Access(Principal principal){
        if(principal == null){
            return null;
        }
        return "You Have Access";
    }
}
