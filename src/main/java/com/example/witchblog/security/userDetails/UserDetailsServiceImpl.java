package com.example.witchblog.security.userDetails;

import com.example.witchblog.exceptions.EmailConfirmationException;
import com.example.witchblog.exceptions.NotConfirmedEmailException;
import com.example.witchblog.models.User;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findUserByEmail(email);
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        User user = findUserById(id);
        return UserDetailsImpl.build(user);
    }

    private User findUserByEmail(String email) {
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
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
                .orElseThrow(() -> new UsernameNotFoundException(String.valueOf(id)));
    }
}
