package com.se100.clinic_management.Interface;

import java.util.List;

import com.se100.clinic_management.dto.RoleDto;
import com.se100.clinic_management.model.Role;

public interface iRoleService {
    List<RoleDto> getAllRole();
}
