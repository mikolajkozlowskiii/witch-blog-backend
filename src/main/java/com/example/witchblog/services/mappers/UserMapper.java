package com.example.witchblog.services.mappers;

import com.example.witchblog.models.AuthProvider;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.request.UpdateUserRequest;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    public User map(SignUpRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();
    }

    public User map(UpdateUserRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }

    public User newLocalUserMap(SignUpRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .isEnabled(false)
                .provider(AuthProvider.local)
                .roles(roleService.getRolesForUser())
                .build();
    }

    public UserResponse map(User user){
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }
}
