package com.example.witchblog.controllers;

import com.example.witchblog.email.entity.ConfirmationToken;
import com.example.witchblog.email.services.ConfirmationTokenServiceImpl;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.LoginRequest;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.JwtResponse;
import com.example.witchblog.payload.response.MessageResponse;
import com.example.witchblog.security.oauth2.GoogleOAuth2UserInfo;
import com.example.witchblog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    private ConfirmationTokenServiceImpl confirmationTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        final JwtResponse jwtResponse = authService.signIn(loginRequest);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        if (!authService.checkEmailAvailability(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        User createdUser = authService.createUser(signUpRequest);

        confirmationTokenService.sendConfirmationEmail(createdUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/oauth2/token")
    @ResponseStatus(HttpStatus.OK)
    public void authenticateUser() {}

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token){
        return ResponseEntity.ok(confirmationTokenService.confirmToken(token));
    }

    @GetMapping(path = "confirmationEmail/{email}")
    public ResponseEntity<?> sendConfrimationEmail(@PathVariable String email){
        confirmationTokenService.sendConfirmationEmail(email);

        return ResponseEntity.ok("Email sent to: " + email);
    }

}
