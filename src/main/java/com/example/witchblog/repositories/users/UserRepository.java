package com.example.witchblog.repositories.users;

import com.example.witchblog.entity.users.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Transactional
    @Modifying
    @Query("UPDATE User a " +
            "SET a.isEnabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
    @Transactional
    Optional<User> findById(Long id);
    //Optional<User> findByUsernameOrEmail(@NotBlank @Size(max = 15) String username,
    //                                     @NotBlank @Size(max = 40) @Email String email);
}
