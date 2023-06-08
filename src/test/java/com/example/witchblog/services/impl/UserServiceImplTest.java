package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.UserNotFoundException;
import com.example.witchblog.entity.users.ERole;
import com.example.witchblog.entity.users.Role;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.dto.users.response.UserResponse;
import com.example.witchblog.repositories.users.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.users.RoleService;
import com.example.witchblog.services.users.impl.UserServiceImpl;
import com.example.witchblog.services.users.mappers.UserMapper;
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
    public void findCurrentUser_CurrentUser_ReturnsUserResponse() {
        UserResponse expectedResponse = new UserResponse(user.getId(), user.getEmail(), user.getFirstName(),user.getLastName(), user.getBirthDate());
        UserResponse actualResponse = userService.findCurrentUserResponse(userDetails);

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
    @Test
    public void findUserByEmail_EmailFounded_ReturnsUserResponse() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userMapper.map(user)).thenReturn(new UserResponse(user.getId(),user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate()));

        UserResponse expectedResponse = new UserResponse(user.getId() ,user.getEmail(), user.getFirstName(), user.getLastName(), user.getBirthDate());
        UserResponse actualResponse = userService.findUserResponseByEmail(user.getEmail());

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void findUserByEmail_EmailNotFounded_ThrowsUserNotFoundException() {
        String emailNotFoundInRepo = "Email not founded in repo";
        when(userRepository.findByEmail(emailNotFoundInRepo)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserResponseByEmail(emailNotFoundInRepo));
    }
    @Test
    void findCurrentUser_UserDetailsValid_ReturnsUserResponse() {
        UserResponse expectedResponse = UserResponse
                .builder()
                .firstName(userDetails.getFirstName())
                .lastName(userDetails.getLastName())
                .email(userDetails.getEmail())
                .build();

        UserResponse acutalResponse = userService.findCurrentUserResponse(userDetails);

        Assertions.assertEquals(expectedResponse, acutalResponse);
    }

    @Test
    void findCurrentUser_UserDetailsIsNull_ThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.findCurrentUserResponse(null));
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
    void findUserResponseByEmail() {

    }

    @Test
    void findUserByEmail_EmailBelongsToUser_ReturnsUser() {
        final User expectedUser = user;
        final String usersEmail = user.getEmail();
        when(userRepository.findByEmail(usersEmail)).thenReturn(Optional.of(user));

        final User actualUser = userService.findUserByEmail(usersEmail);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void findUserByEmail_EmailDoesntBelongToAnyUser_ThrownUserNotFoundException() {
        final String emailNotFoundInRepo = user.getEmail();
        when(userRepository.findByEmail(emailNotFoundInRepo)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserByEmail(emailNotFoundInRepo));
    }

    @Test
    void findUserById() {
        final User expectedUser = user;
        final Long userId = user.getId();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        final User actualUser = userService.findUserById(userId);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void findUserByEmail22() {
        final Long userIdNotInRepo = 123L;
        when(userRepository.findById(userIdNotInRepo)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findUserById(userIdNotInRepo));
    }
}
