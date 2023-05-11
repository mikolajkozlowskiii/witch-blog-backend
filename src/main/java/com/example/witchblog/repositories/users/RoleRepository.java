package com.example.witchblog.repositories.users;

import com.example.witchblog.entity.users.ERole;
import com.example.witchblog.entity.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
