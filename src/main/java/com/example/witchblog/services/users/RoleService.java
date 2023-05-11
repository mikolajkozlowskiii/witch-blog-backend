package com.example.witchblog.services.users;

import com.example.witchblog.entity.users.ERole;
import com.example.witchblog.entity.users.Role;
import com.example.witchblog.entity.users.User;

import java.util.Set;

public interface RoleService {
    Set<Role> getRolesForUser();
    Role getRole(ERole role);
    boolean checkIfUserHasRole(User user, Role role);
}
