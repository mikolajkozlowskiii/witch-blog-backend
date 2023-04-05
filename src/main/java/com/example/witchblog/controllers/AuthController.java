package com.example.witchblog.controllers;

import com.example.witchblog.email.entity.ConfirmationToken;
import com.example.witchblog.email.services.ConfirmationTokenServiceImpl;
import com.example.witchblog.models.User;
import com.example.witchblog.payload.request.LoginRequest;
import com.example.witchblog.payload.request.SignUpRequest;
import com.example.witchblog.payload.response.JwtResponse;
import com.example.witchblog.payload.response.MessageResponse;
import com.example.witchblog.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



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

        User createdUser = authService.createUser(signUpRequest);

        // TODO REPLACE THOSE 2 LINES ON confirmationTokenService.sendConfirmationEmail();
        confirmationTokenService.sendConfirmationEmail(createdUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirmToken(@RequestParam("token") String token){
        return ResponseEntity.ok(confirmationTokenService.confirmToken(token));
    }

    @GetMapping(path = "{email}/sendEmail")
    public ResponseEntity<?> sendConfrimationEmail(@PathVariable String email){
        confirmationTokenService.sendConfirmationEmail(email);

        return ResponseEntity.ok("email sended to " + email);
    }

}
