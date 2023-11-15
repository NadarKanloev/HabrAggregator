package com.example.spring_security_tutorial.repository;

import com.example.spring_security_tutorial.model.ERole;
import com.example.spring_security_tutorial.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
