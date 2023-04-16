package com.example.witchblog.services.impl;

import com.example.witchblog.exceptions.AppException;
import com.example.witchblog.exceptions.RoleNotFoundException;
import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;
import com.example.witchblog.repositories.RoleRepository;
import com.example.witchblog.repositories.UserRepository;
import com.example.witchblog.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    public Set<Role> getRolesForUser() {
        Set<Role> roles = new HashSet<>();
        if (userRepository.count() == 0) {
            roles.add(getRole(ERole.ROLE_USER));
            roles.add(getRole(ERole.ROLE_ADMIN));
        } else {
            roles.add(getRole(ERole.ROLE_USER));
        }

        return roles;
    }
    public Role getRole(ERole role) {
        return roleRepository
                .findByName(role)
                .orElseThrow(() -> new RoleNotFoundException("Put in DB role" + role));
    }
    @Override
    public boolean checkIfUserHasRole(User user, Role role) {
        return user.getRoles().contains(role);
    }
}
