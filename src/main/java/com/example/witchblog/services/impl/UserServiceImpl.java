package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.AppException;
import com.example.witchblog.exceptions.RoleNotFoundException;
import com.example.witchblog.exceptions.UnauthorizedException;
import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.repositories.RoleRepository;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.security.services.UserDetailsImpl;
import com.example.witchblog.services.UserService;
import com.example.witchblog.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse getCurrentUser(UserDetailsImpl currentUser) {
        return userMapper.map(currentUser);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = findUserByUsername(username);
        return userMapper.map(user);
    }

    @Override
    public UserResponse updateUser(SignUpRequest request, String username, UserDetailsImpl currentUser) {
        User user = findUserByUsername(username);
        if(user.getId().equals(currentUser.getId())){
            long userId = user.getId();
            Set<Role> userRoles = user.getRoles();
            user = userMapper.map(request);
            user.setId(userId);
            user.setRoles(userRoles);


            return userMapper.map(userRepository.save(user));
        }
        throw new UnauthorizedException("update not your account");
    }

    @Override
    public boolean deleteUser(String username, UserDetailsImpl currentUser) {
        User user = findUserByUsername(username);
        if(user.getId().equals(currentUser.getId()) ||
                currentUser.getAuthorities().contains(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name()))){
            userRepository.delete(user);
            return true;
        }
        throw new UnauthorizedException("delete not your account");
    }

    @Override
    public ApiResponse giveModerator(String username) {
        User user = findUserByUsername(username);
        Role roleModerator = findRoleModerator();
        if(user.getRoles().contains(roleModerator)){
            throw new AppException(username + " has already role " + ERole.ROLE_MODERATOR.name());
        }
        user.getRoles().add(roleRepository.findByName(ERole.ROLE_MODERATOR).orElseThrow(
                () -> new RoleNotFoundException("Put in DB role 'ROLE_MODERATOR'")));
        userRepository.save(user);

        return new ApiResponse(Boolean.TRUE, "Moderator role set to user: " + username);
    }

    @Override
    public ApiResponse removeModerator(String username) {
        User user = findUserByUsername(username);
        Role roleModerator = findRoleModerator();
        if(!user.getRoles().contains(roleModerator)){
            throw new AppException(username + " hasn't got role " + ERole.ROLE_MODERATOR.name());
        }
        user.getRoles().remove(roleModerator);
        userRepository.save(user);

        return new ApiResponse(Boolean.TRUE, "Moderator role removed from user: " + username);
    }

    private User findUserByUsername(String username) {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return user;
    }

    private Role findRoleModerator() {
        Role roleModerator = roleRepository
                .findByName(ERole.ROLE_MODERATOR)
                .orElseThrow(() -> new RoleNotFoundException("Put in DB role 'ROLE_MODERATOR'"));
        return roleModerator;
    }
}
