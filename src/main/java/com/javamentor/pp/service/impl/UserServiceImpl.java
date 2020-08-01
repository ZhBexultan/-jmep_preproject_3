package com.javamentor.pp.service.impl;

import com.javamentor.pp.model.Role;
import com.javamentor.pp.model.User;
import com.javamentor.pp.repository.UserRepository;
import com.javamentor.pp.service.RoleService;
import com.javamentor.pp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public void addUser(User user, Set<Long> roles_id) {
        Set<Role> roles = roles_id.stream().map(roleService::getRoleById).collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user, Set<Long> roles_id) {
        Set<Role> roles = roles_id.stream().map(roleService::getRoleById).collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }
}
