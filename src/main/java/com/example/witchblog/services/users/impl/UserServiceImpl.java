package com.example.witchblog.services.users.impl;

import com.example.witchblog.exceptions.AppException;
import com.example.witchblog.exceptions.UnauthorizedException;
import com.example.witchblog.exceptions.UserNotFoundException;
import com.example.witchblog.entity.users.ERole;
import com.example.witchblog.entity.users.Role;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.dto.users.request.UpdateUserRequest;
import com.example.witchblog.dto.response.ApiResponse;
import com.example.witchblog.dto.users.response.UserResponse;
import com.example.witchblog.repositories.users.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.users.RoleService;
import com.example.witchblog.services.users.UserService;
import com.example.witchblog.services.users.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserResponse findCurrentUserResponse(UserDetailsImpl userDetails) {
            return userMapper.map(findCurrentUser(userDetails));
    }

    @Override
    public User findCurrentUser(UserDetailsImpl userDetails) {
        if(Objects.isNull(userDetails)){
            throw new IllegalArgumentException("UserDetails instance can't be null");
        }
        return findUserById(userDetails.getId());
    }

    @Override
    public UserResponse findUserResponseByEmail(String email) {
        return userMapper.map(findUserByEmail(email));
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest updateInfoRequest, String email, UserDetailsImpl currentUser) {
        User user = findUserByEmail(email);
        if(user.getId().equals(currentUser.getId())){
            User updatedUser = userMapper.map(user, updateInfoRequest);
            return userMapper.map(userRepository.save(updatedUser));
        }
        throw new UnauthorizedException("Can't update not your account.");
    }

    @Override
    @Transactional
    public boolean deleteUser(String email, UserDetailsImpl currentUser) {
        User user = findUserByEmail(email);
        if(user.getId().equals(currentUser.getId()) ||
                currentUser.getAuthorities().contains(new SimpleGrantedAuthority(ERole.ROLE_ADMIN.name()))){
            userRepository.delete(user);
            return true;
        }
        throw new UnauthorizedException("Can't delete not your account.");
    }

    @Override
    public ApiResponse giveModerator(String email) {
        User user = findUserByEmail(email);

        Role roleModerator = roleService.getRole(ERole.ROLE_MODERATOR);
        if(roleService.checkIfUserHasRole(user, roleModerator)){
            throw new AppException(email + " has already role " + ERole.ROLE_MODERATOR.name());
        }

        user.getRoles().add(roleModerator);
        userRepository.save(user);

        return new ApiResponse(Boolean.TRUE, "Moderator role set to user: " + email);
    }

    @Override
    public ApiResponse removeModerator(String email) {
        User user = findUserByEmail(email);

        Role roleModerator = roleService.getRole(ERole.ROLE_MODERATOR);
        if(!roleService.checkIfUserHasRole(user, roleModerator)){
            throw new AppException(email + " hasn't got role " + ERole.ROLE_MODERATOR.name());
        }

        user.getRoles().remove(roleModerator);
        userRepository.save(user);

        return new ApiResponse(Boolean.TRUE, "Moderator role removed from user: " + email);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }
}
