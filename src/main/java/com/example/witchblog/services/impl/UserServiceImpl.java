package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.AppException;
import com.example.witchblog.exceptions.UnauthorizedException;
import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.request.UpdateUserRequest;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.RoleService;
import com.example.witchblog.services.UserService;
import com.example.witchblog.services.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public UserResponse getCurrentUser(UserDetailsImpl userDetails) {
            return UserResponse.builder()
                    .firstName(userDetails.getFirstName())
                    .lastName(userDetails.getLastName())
                    .email(userDetails.getEmail())
                    .build();
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return userMapper.map(findUserByEmail(email));
    }

    @Override
    public UserResponse updateUser(UpdateUserRequest request, String email, UserDetailsImpl currentUser) {
        User user = findUserByEmail(email);
        if(user.getId().equals(currentUser.getId())){
            User updatedUser = userMapper.map(request);
            updatedUser.setId(user.getId());
            updatedUser.setEnabled(user.isEnabled());
            updatedUser.setRoles(user.getRoles());
            updatedUser.setProvider(user.getProvider());
            return userMapper.map(userRepository.save(user));
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
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }
}
