package com.example.witchblog.services.users.impl;

import com.example.witchblog.exceptions.RoleNotFoundException;
import com.example.witchblog.entity.users.ERole;
import com.example.witchblog.entity.users.Role;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.repositories.users.RoleRepository;
import com.example.witchblog.repositories.users.UserRepository;
import com.example.witchblog.services.users.RoleService;
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
