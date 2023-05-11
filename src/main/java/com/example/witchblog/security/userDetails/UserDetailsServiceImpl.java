package com.example.witchblog.security.userDetails;

import com.example.witchblog.exceptions.NotConfirmedEmailException;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.exceptions.UserNotFoundException;
import com.example.witchblog.repositories.users.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
        User user = findUserByEmail(email);
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UserNotFoundException {
        User user = findUserById(id);
        return UserDetailsImpl.build(user);
    }

    private User findUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        checkIsUserEnabled(user);
        return user;
    }

    private static void checkIsUserEnabled(User user) {
        if(!user.isEnabled()){
            throw new NotConfirmedEmailException(user.getEmail());
        }
    }


    private User findUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.valueOf(id)));
    }
}
