package com.example.witchblog.services;

import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.LoginRequest;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.JwtResponse;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    User createUser(SignUpRequest request);
    JwtResponse signIn(LoginRequest request);
    boolean checkEmailAvailability(String email);
    boolean checkUsernameAvailability(String username);
}
