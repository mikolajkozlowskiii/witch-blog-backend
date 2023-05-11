package com.example.witchblog.services.users;

import com.example.witchblog.entity.users.User;
import com.example.witchblog.dto.users.request.UpdateUserRequest;
import com.example.witchblog.dto.response.ApiResponse;
import com.example.witchblog.dto.users.response.UserResponse;
import com.example.witchblog.security.userDetails.UserDetailsImpl;

public interface UserService {
    UserResponse findCurrentUserResponse(UserDetailsImpl currentUser);
    UserResponse findUserResponseByEmail(String email);
    UserResponse updateUser(UpdateUserRequest updatedUser, String username, UserDetailsImpl currentUser);
    boolean deleteUser(String username, UserDetailsImpl currentUser);
    ApiResponse giveModerator(String username);
    ApiResponse removeModerator(String username);
    User findUserByEmail(String email);
    User findUserById(Long id);
}
