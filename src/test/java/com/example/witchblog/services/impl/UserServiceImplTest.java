package com.example.witchblog.services.impl;

import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.RoleService;
import com.example.witchblog.services.mappers.UserMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    private User user;
    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(ERole.ROLE_USER));
        user.setRoles(roles);

        userDetails = UserDetailsImpl.build(user);
    }

    @Test
    @DisplayName("getCurrentUser returns UserResponse with correct data")
    public void getCurrentUser_CurrentUser_ReturnsUserResponse() {
        UserResponse expectedResponse = new UserResponse(user.getEmail(), user.getFirstName(),user.getLastName());
        UserResponse actualResponse = userService.getCurrentUser(userDetails);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void getUserByEmail_EmailFounded_ReturnsUserResponse() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(new UserResponse(user.getEmail(), user.getFirstName(), user.getLastName()));

        UserResponse expectedResponse = new UserResponse(user.getEmail(), user.getFirstName(), user.getLastName());
        UserResponse actualResponse = userService.getUserByEmail(user.getEmail());

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void getUserByEmail_EmailNotFounded_ThrowsUserNotFoundException() {
        String emailNotFoundInRepo = "Email not founded in repo";
        when(userRepository.findByEmail(emailNotFoundInRepo)).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> userService.getUserByEmail(emailNotFoundInRepo));
    }
    @Test
    void getCurrentUser() {
    }

    @Test
    void getUserByEmail() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void giveModerator() {
    }

    @Test
    void removeModerator() {
    }

    @Test
    void findUserByEmail() {
    }

    @Test
    void findUserById() {
    }
}