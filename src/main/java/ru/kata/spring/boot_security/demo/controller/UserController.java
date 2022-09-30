package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String redirectToLoginPage() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String redirectUserToProfile(ModelMap model,Principal principal) {
        User user = userService.findOneUserByName(principal.getName());
        model.addAttribute("user",user);
        return "userPage";
    }

    @GetMapping("/admin")
    public String getAllUsers(Model model) {
        model.addAttribute("users",userService.findAllUsers());
        return "/admin/allUsers";
    }

    @GetMapping("/admin/new")
    public String getNewUserForm(Model model) {
        model.addAttribute("user",new User());
        model.addAttribute("listRoles",userService.getListRoles());
        return "/admin/new";
    }

    @PostMapping("/new")
    public String addNewUser(@ModelAttribute("user") User user,@RequestParam(value = "role") String role) {
        user.setRoles(userService.findRoleByName(role));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}")
    public String getUserById(@PathVariable("id") int id,Model model) {
        model.addAttribute("user",userService.findOneUserById(id));
        return "/admin/show";
    }

    @GetMapping("/admin/edit/{id}")
    private String getUpdatedUserData(@PathVariable("id") int id,Model model) {
        model.addAttribute("user",userService.findOneUserById(id));
        model.addAttribute("listRoles",userService.getListRoles());
        return "admin/edit";
    }

    @PutMapping("/admin/edit")
    public String setUpdatedUserData(@ModelAttribute("user") User user,@RequestParam(value = "role") String role) {
        user.setRoles(userService.findRoleByName(role));
        userService.updateUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUserById(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}