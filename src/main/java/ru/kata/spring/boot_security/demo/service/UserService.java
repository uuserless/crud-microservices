package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    boolean saveUser(User user);

    List<User> findAllUsers();

    User findOneUserById(int id);

    User findOneUserByName(String name);

    void updateUser(User user, int id);

    void deleteUser(int id);

}
