package com.javamentor.pp.controller;

import com.javamentor.pp.model.Role;
import com.javamentor.pp.model.User;
import com.javamentor.pp.service.RoleService;
import com.javamentor.pp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /*@GetMapping("/user")
    public String getUserById(@AuthenticationPrincipal Principal principal, ModelMap map) {
        User user = userService.getUserByUsername(principal.getName());
        map.addAttribute("active_user", user);
        return "user";
    }*/

/*    @GetMapping({"/", "/admin"})
    public String getUsers(@AuthenticationPrincipal Principal principal,
                           ModelMap map) {
        List<User> users = userService.getAllUsers();
        List<Role> roles = roleService.getAllRoles();
        User user = userService.getUserByUsername(principal.getName());
        map.addAttribute("active_user", user);
        map.addAttribute("roles", roles);
        map.addAttribute("users", users);
        return "admin";
    }*/

    /*@GetMapping("/admin/addNewUser")
    public String newUser(@AuthenticationPrincipal Principal principal,
                           ModelMap map) {
        List<Role> roles = roleService.getAllRoles();
        User user = userService.getUserByUsername(principal.getName());
        map.addAttribute("active_user", user);
        map.addAttribute("roles", roles);
        return "addNewUser";
    }*/

/*    @GetMapping("/admin/user/{id}")
    public String getUserByIdUpdate(@PathVariable Long id,
                                    ModelMap map) {
        User user = userService.getUserById(id);
        List<Role> roles = roleService.getAllRoles();
        List<Long> uroles = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        map.addAttribute("uroles", uroles);
        map.addAttribute("roles", roles);
        map.addAttribute("user", user);
        return "addNewUser";
    }*/

    @PostMapping("/admin/user")
    public String addUser(@ModelAttribute User user,
                          @RequestParam Set<Long> roles_id){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user, roles_id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/updateUser")
    public String editUser(@ModelAttribute User user,
                           @RequestParam Set<Long> roles_id) {
        String userPassword = userService.getUserById(user.getId()).getPassword();
        if (!(userPassword.equals(user.getPassword()) ||
                userPassword.equals(passwordEncoder.encode(user.getPassword())))) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userService.updateUser(user, roles_id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam Long id){
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
