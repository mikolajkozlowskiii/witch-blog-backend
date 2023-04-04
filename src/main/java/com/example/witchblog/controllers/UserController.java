package com.example.witchblog.controllers;

import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.ApiResponse;
import com.example.witchblog.payload.response.MessageResponse;
import com.example.witchblog.payload.response.UserResponse;
import com.example.witchblog.security.CurrentUser;
import com.example.witchblog.security.services.UserDetailsImpl;
import com.example.witchblog.services.AuthService;
import com.example.witchblog.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser UserDetailsImpl currentUser){
        UserResponse userResponse = userService.getCurrentUser(currentUser);
        System.out.println(userResponse);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getCurrentUser(@PathVariable(value = "username") String username){
        UserResponse userResponse = userService.getUserByUsername(username);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @CacheEvict(value = "users", key = "#username")
    public ResponseEntity<?> updateUser(@PathVariable(value = "username") String username,
                                                          @Valid @RequestBody SignUpRequest signUpRequest,
                                                          @CurrentUser UserDetailsImpl currentUser){
        if (!authService.checkUsernameAvailability(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (!authService.checkEmailAvailability(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        UserResponse userResponse = userService.updateUser(signUpRequest, username, currentUser);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/users/{username}")
                .buildAndExpand(userResponse.getUsername())
                .toUri();

        return ResponseEntity.created(location).body(userResponse);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username,
                                                          @CurrentUser UserDetailsImpl currentUser){
        userService.deleteUser(username, currentUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{username}/giveMod")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> giveMod(@PathVariable(value = "username") String username){
        System.out.println("debugd");
        final ApiResponse apiResponse = userService.giveModerator(username);

        return new ResponseEntity< >(apiResponse, HttpStatus.OK);
    }

    @PutMapping("/{username}/removeMod")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> removeMode(@PathVariable(value = "username") String username){
        final ApiResponse apiResponse = userService.removeModerator(username);

        return new ResponseEntity< >(apiResponse, HttpStatus.OK);
    }
}
