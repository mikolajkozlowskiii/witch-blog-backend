package com.example.witchblog.services;

import com.example.witchblog.models.ERole;
import com.example.witchblog.models.Role;
import com.example.witchblog.models.User;

import java.util.Set;

public interface RoleService {
    Set<Role> getRolesForUser();
    Role getRole(ERole role);
    boolean checkIfUserHasRole(User user, Role role);
}
