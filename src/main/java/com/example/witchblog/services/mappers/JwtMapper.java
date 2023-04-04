package com.example.witchblog.services.mappers;

import com.example.witchblog.payload.response.JwtResponse;
import com.example.witchblog.security.services.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JwtMapper {
    public JwtResponse map(String tokenJwt, UserDetailsImpl userDetails, List<String> roles){
        return JwtResponse.builder()
                .token(tokenJwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles)
                .build();
    }
}
