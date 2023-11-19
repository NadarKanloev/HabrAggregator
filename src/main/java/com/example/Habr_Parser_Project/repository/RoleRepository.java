package com.example.Habr_Parser_Project.repository;

import com.example.Habr_Parser_Project.model.ERole;
import com.example.Habr_Parser_Project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
