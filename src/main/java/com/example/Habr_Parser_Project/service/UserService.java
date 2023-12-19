package com.example.Habr_Parser_Project.service;

import com.example.Habr_Parser_Project.repository.UserRepository;
import com.example.Habr_Parser_Project.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    public long getID() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (userDetails != null && userRepository.existsById(userDetails.getId())) {
            return userDetails.getId();
        }
        return -1;
    }
}
