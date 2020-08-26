package com.javamentor.pp.dto;

import com.javamentor.pp.model.Role;
import lombok.Data;

@Data
public class RoleDto {

    private Long id;
    private String name;

    public Role toRole() {
        Role role = new Role();
        role.setId(id);
        role.setRole(name);
        return role;
    }

    public static RoleDto fromRole (Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getRole());
        return roleDto;
    }



}
