package com.example.Habr_Parser_Project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorld {
    @GetMapping
    public String HelloWorld(){
        return "Hello World";
    }
}
