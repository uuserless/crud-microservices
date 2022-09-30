package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    List<User> findAllUsers();

    User findOneUserById(int id);

    User findOneUserByName(String name);

    void updateUser(User user);

    void deleteUser(int id);

    List<Role> getListRoles();

    List<Role> findRoleByName(String role);
}
