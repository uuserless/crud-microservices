package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

    boolean saveUser(User user);

    List<User> findAllUsers();

    User findOneUserById(int id);

    User findOneUserByName(String name);

    void updateUser(User user, int id);

    void deleteUser(int id);

}
