package com.example.witchblog.services.impl;

import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.LoginRequest;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.JwtResponse;
import com.example.witchblog.repositories.RoleRepository;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.security.jwt.JwtUtils;
import com.example.witchblog.security.services.UserDetailsImpl;
import com.example.witchblog.services.AuthService;
import com.example.witchblog.services.mappers.JwtMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final JwtMapper jwtMapper;

    @Override
    public User createUser(SignUpRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        Set<Role> roles = getRoles();
        user.setRoles(roles);

        return userRepository.save(user);
    }

    private Set<Role> getRoles() {
        Set<Role> roles = new HashSet<>();

        if (userRepository.count() == 0) {
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new UsernameNotFoundException("temporary")));
            roles.add(roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new UsernameNotFoundException("temporary")));
        } else {
            roles.add(roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new UsernameNotFoundException("temporary")));
        }

        return roles;
    }

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return jwtMapper.map(jwt, userDetails, roles);
    }

    @Override
    public boolean checkEmailAvailability(String email) {
        return !userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkUsernameAvailability(String username) {
        return !userRepository.existsByUsername(username);
    }
}
