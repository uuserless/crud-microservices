package ru.uuserless.example.userservice.service;

import java.util.List;
import ru.uuserless.example.userservice.model.User;

public interface UserService {

    boolean saveUser(User user);

    List<User> findAllUsers();

    User findOneUserById(Long id);

    User findOneUserByName(String name);

    void updateUser(User user, Long id);

    void deleteUser(Long id);

}
