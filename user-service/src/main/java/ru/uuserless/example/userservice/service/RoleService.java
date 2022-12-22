package ru.uuserless.example.userservice.service;

import java.util.List;
import ru.uuserless.example.userservice.model.Role;

public interface RoleService {

    List<Role> getListRoles();

    Role findRoleById(Long id);

}
