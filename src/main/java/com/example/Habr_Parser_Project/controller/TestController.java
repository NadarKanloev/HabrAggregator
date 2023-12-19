package com.example.Habr_Parser_Project.controller;

import com.example.Habr_Parser_Project.security.UserDetailsImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/test")
@Log4j2
public class TestController {
    @GetMapping("/all")
    public String allAccess(){
        return "public content";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess(){
        return "user content";
    }
    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess(){
        return "moderator content";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Admin Board";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public UserDetailsImpl profile(){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        log.info("username : {}", userDetails.getUsername());
        log.info("id : {}", userDetails.getId());
        log.info("role : {}", userDetails.getAuthorities());
        return userDetails;
    }
}
