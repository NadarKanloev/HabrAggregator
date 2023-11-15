package com.example.spring_security_tutorial;

import com.example.spring_security_tutorial.model.ERole;
import com.example.spring_security_tutorial.model.Role;
import com.example.spring_security_tutorial.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class HabrParserProject {
    public static void main(String[] args){
        SpringApplication.run(HabrParserProject.class, args);
    }

}
