package com.example.witchblog.email.services;

import com.example.witchblog.email.entity.ConfirmationToken;
import com.example.witchblog.email.repositories.ConfirmationTokenRepository;
import com.example.witchblog.models.User;
import com.example.witchblog.security.services.UserDetailsImpl;
import com.example.witchblog.services.AuthService;
import com.example.witchblog.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl {
    private final AuthService authService;
    private final UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }


    public ConfirmationToken createConfirmationToken(User currentUser, long expiresTimeInMinutes){
        String token = UUID.randomUUID().toString();

        return ConfirmationToken
                .builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(expiresTimeInMinutes))
                .user(currentUser)
                .build();
    }

    public ConfirmationToken createConfirmationToken(String email, long expiresTimeInMinutes){
        User currentUser = userService.findUserByEmail(email);

        String token = UUID.randomUUID().toString();

        return ConfirmationToken
                .builder()
                .token(token)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(expiresTimeInMinutes))
                .user(currentUser)
                .build();
    }


    @Transactional
    public String confirmToken(String token){
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("email already confirmed!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if(expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException("token expired!");
        }

        setConfirmedAt(token);
        authService.enableUser(confirmationToken.getUser().getEmail());

        return "user confirmed!";
    }

    public int setConfirmedAt(String token){
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now()
        );
    }
}
