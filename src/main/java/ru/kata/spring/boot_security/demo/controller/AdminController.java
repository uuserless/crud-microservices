package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService,RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("currentUser", userService.findOneUserByName(authentication.getName()));
        model.addAttribute("usersList", userService.findAllUsers());
        model.addAttribute("rolesList", roleService.getListRoles());
        model.addAttribute("newUser", new User());
        return "adminPage";
    }

    @PostMapping("/admin/new")
    public String addNewUser(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PutMapping("admin/update/{id}")
    public String setUpdatedUserData(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUser(user, id);
        return "redirect:/admin";
    }

    @DeleteMapping("admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}