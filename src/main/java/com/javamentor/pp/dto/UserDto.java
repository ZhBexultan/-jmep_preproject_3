package com.javamentor.pp.dto;

import com.javamentor.pp.model.Role;
import com.javamentor.pp.model.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private byte age;
    private Set<RoleDto> roles;

    public UserDto() {
    }

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setRoles(roles.stream().map(RoleDto::toRole).collect(Collectors.toSet()));
        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setAge(user.getAge());
        userDto.setRoles(user.getRoles().stream().map(RoleDto::fromRole).collect(Collectors.toSet()));
        return userDto;
    }

}
