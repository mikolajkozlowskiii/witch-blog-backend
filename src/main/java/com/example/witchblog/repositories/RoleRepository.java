package com.example.witchblog.repositories;

import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
