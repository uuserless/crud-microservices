package ru.uuserless.example.userservice.config;

import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import ru.uuserless.example.userservice.model.Role;
import ru.uuserless.example.userservice.model.User;
import ru.uuserless.example.userservice.repository.RoleRepository;
import ru.uuserless.example.userservice.repository.UserRepository;

public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        userRepository.deleteAll();
        roleRepository.deleteAll();

        Role adminRole = Role.builder().role("ROLE_USER").build();
        Role userRole = Role.builder().role("ROLE_USER").build();

        User admin = User.builder()
                .age(30)
                .firstName("adminRole")
                .lastName("adminRole")
                .password("$2a$12$bW5uTXl.QahmCIAZnyyrP.xkJqiUkSYrQSe4HyWCc08a4.LEiPp..")
                .roles(Arrays.asList(adminRole, userRole))
                .email("adminRole@mail.ru")
                .build();
        User user = User.builder()
                .age(18)
                .firstName("userRole")
                .lastName("userRole")
                .password("$2a$12$bW5uTXl.QahmCIAZnyyrP.xkJqiUkSYrQSe4HyWCc08a4.LEiPp..")
                .roles(Collections.singletonList(userRole))
                .email("userRole@mail.ru")
                .build();

        roleRepository.save(adminRole);
        roleRepository.save(userRole);

        userRepository.save(admin);
        userRepository.save(user);

    }
}
