package com.javamentor.pp.service;

import com.javamentor.pp.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    void addUser(User user, Set<Long> roles_id);
    void updateUser(User user, Set<Long> roles_id);
    void deleteUser(Long id);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);

}
