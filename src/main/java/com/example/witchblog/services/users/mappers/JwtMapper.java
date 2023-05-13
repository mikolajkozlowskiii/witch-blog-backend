package com.example.witchblog.services.users.mappers;

import com.example.witchblog.dto.response.JwtResponse;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtMapper {
    public JwtResponse map(String tokenJwt, UserDetailsImpl userDetails, List<String> roles){
        return JwtResponse.builder()
                .token(tokenJwt)
                .id(userDetails.getId())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }
}
