package com.example.witchblog.services;

import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.request.UpdateUserRequest;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.security.userDetails.UserDetailsImpl;

public interface UserService {
    UserResponse getCurrentUser(UserDetailsImpl currentUser);
    UserResponse getUserByEmail(String email);
    UserResponse updateUser(UpdateUserRequest updatedUser, String username, UserDetailsImpl currentUser);
    boolean deleteUser(String username, UserDetailsImpl currentUser);
    ApiResponse giveModerator(String username);
    ApiResponse removeModerator(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);
}
