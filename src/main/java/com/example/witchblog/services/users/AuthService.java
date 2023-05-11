package com.example.witchblog.services.users;

import com.example.witchblog.entity.users.User;
import com.example.witchblog.dto.users.request.LoginRequest;
import com.example.witchblog.dto.users.request.SignUpRequest;
import com.example.witchblog.dto.response.JwtResponse;

public interface AuthService {
    User createUser(SignUpRequest request);
    JwtResponse signIn(LoginRequest request);
    boolean checkEmailAvailability(String email);
    int enableUser(String email);
}
