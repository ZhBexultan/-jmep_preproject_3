package com.javamentor.pp.controller;

import com.javamentor.pp.dto.RoleDto;
import com.javamentor.pp.dto.UserDto;
import com.javamentor.pp.model.User;
import com.javamentor.pp.service.RoleService;
import com.javamentor.pp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class UserRestController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/activeUser")
    public UserDto getActiveUser(@AuthenticationPrincipal Principal activeUser) {
        return UserDto.fromUser(userService.getUserByUsername(activeUser.getName()));
    }

    @GetMapping("/users")
    public List<UserDto> getAllUsersDto() {
        return userService.getAllUsers().stream().map(UserDto::fromUser).collect(Collectors.toList());
    }

    @GetMapping("/roles")
    public List<RoleDto> getAllRoelsDto() {
        return roleService.getAllRoles().stream().map(RoleDto::fromRole).collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserDto getUserDtoById(@PathVariable Long id) {
        return UserDto.fromUser(userService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/users")
    public UserDto addUserDto(@RequestBody UserDto userDto) {
        User user = userDto.toUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return userDto;
    }

    @PutMapping("/users/{id}")
    public UserDto updateUserDto(@RequestBody UserDto userDto,
                                 @PathVariable Long id) {
        User user = userDto.toUser();
        User userFromDb = userService.getUserById(id);
        user.setId(id);
        if (!(user.getPassword().equals("") || user.getPassword() == null)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setPassword(userFromDb.getPassword());
        userService.addUser(user);
        return userDto;
    }
}
