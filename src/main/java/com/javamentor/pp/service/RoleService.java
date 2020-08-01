package com.javamentor.pp.service;

import com.javamentor.pp.model.Role;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);
    List<Role> getAllRoles();

}
