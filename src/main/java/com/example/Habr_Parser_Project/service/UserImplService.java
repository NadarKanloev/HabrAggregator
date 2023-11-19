package com.example.Habr_Parser_Project.service;

import com.example.Habr_Parser_Project.model.User;
import com.example.Habr_Parser_Project.repository.UserRepository;
import com.example.Habr_Parser_Project.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserImplService implements UserDetailsService {
    private UserRepository userRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetailsImpl loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByname(name).orElseThrow(() -> new UsernameNotFoundException("User not found with username" + name));
        return UserDetailsImpl.build(user);
    }
}