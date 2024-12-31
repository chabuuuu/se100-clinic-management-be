package com.se100.clinic_management.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se100.clinic_management.Interface.iMenuService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.AppMenu;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final iMenuService menuService;

    @GetMapping("")
    public ResponseEntity<ResponseVO> getMyMenu() {
        List<AppMenu> results = menuService.getMyMenu();

        return ResponseEntityGenerator.find(results);
    }

    @PutMapping("update-role-menu/{roleId}")
    public ResponseEntity<ResponseVO> updateRoleMenu(@PathVariable String roleId, @RequestBody List<String> menuIds) {
        menuService.updateRoleMenu(roleId, menuIds);

        return ResponseEntityGenerator.updateFormat("Update role menu successfully");
    }

    @GetMapping("all")
    public ResponseEntity<ResponseVO> getAll() {
        List<AppMenu> results = menuService.getAllMenu();

        return ResponseEntityGenerator.find(results);
    }

    @GetMapping("by-role/{roleId}")
    public ResponseEntity<ResponseVO> getMethodName(@PathVariable String roleId) {
        List<AppMenu> results = menuService.getRoleMenu(roleId);
        return ResponseEntityGenerator.find(results);
    }

}
