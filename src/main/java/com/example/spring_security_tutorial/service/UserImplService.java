package com.example.spring_security_tutorial.service;

import com.example.spring_security_tutorial.model.User;
import com.example.spring_security_tutorial.repository.UserRepository;
import com.example.spring_security_tutorial.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

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