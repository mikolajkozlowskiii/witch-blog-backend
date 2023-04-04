package com.example.witchblog.repositories;

import com.example.witchblog.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(@NotBlank @Size(max = 15) String username,
                                         @NotBlank @Size(max = 40) @Email String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
