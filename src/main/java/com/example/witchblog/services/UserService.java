package com.example.witchblog.services;

import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;

import java.nio.file.attribute.UserPrincipal;

public interface UserService {
    UserResponse getCurrentUser(UserDetailsImpl currentUser);
    UserResponse getUserByUsername(String username);
    UserResponse updateUser(SignUpRequest updatedUser, String username, UserDetailsImpl currentUser);
    boolean deleteUser(String username, UserDetailsImpl currentUser);
    ApiResponse giveModerator(String username);
    ApiResponse removeModerator(String username);
}
