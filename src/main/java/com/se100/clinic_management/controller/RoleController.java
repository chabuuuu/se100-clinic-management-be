package com.se100.clinic_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se100.clinic_management.Interface.iRoleService;
import com.se100.clinic_management.dto.RoleDto;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.Role;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final iRoleService roleService;

    @GetMapping("")
    public ResponseEntity<ResponseVO> getAll() {
        List<RoleDto> results = roleService.getAllRole();
        return ResponseEntityGenerator.find(results);
    }

}
