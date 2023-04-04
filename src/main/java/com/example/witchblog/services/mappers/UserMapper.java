package com.example.witchblog.services.mappers;

import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.security.services.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;
    public User map(SignUpRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    public UserResponse map(User user){
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public UserResponse map(UserDetailsImpl userDetails){
        return UserResponse.builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .build();
    }
}
