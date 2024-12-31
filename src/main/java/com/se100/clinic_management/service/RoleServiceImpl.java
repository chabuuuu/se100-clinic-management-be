package com.se100.clinic_management.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iRoleService;
import com.se100.clinic_management.dto.RoleDto;
import com.se100.clinic_management.model.Role;
import com.se100.clinic_management.repository.RoleRepository;

@Service
public class RoleServiceImpl implements iRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<RoleDto> getAllRole() {
        List<Role> roles = roleRepository.findAll();
        List<RoleDto> roleDtos = new ArrayList<>();

        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setCreateAt(role.getCreateAt());
            roleDto.setCreatedBy(role.getCreatedBy());
            roleDto.setUpdateAt(role.getUpdateAt());
            roleDto.setUpdatedBy(role.getUpdatedBy());
            roleDtos.add(roleDto);
        }

        return roleDtos;
    }

}
