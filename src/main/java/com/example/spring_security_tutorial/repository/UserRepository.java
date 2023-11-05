package com.example.spring_security_tutorial.repository;

import com.example.spring_security_tutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByname(String name);

    boolean existsByname(String name);

    boolean existsByEmail(String email);
}
