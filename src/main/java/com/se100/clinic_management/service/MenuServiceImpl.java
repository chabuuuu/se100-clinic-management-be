package com.se100.clinic_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iMenuService;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.model.AppMenu;
import com.se100.clinic_management.model.Role;
import com.se100.clinic_management.repository.RoleRepository;
import com.se100.clinic_management.utils.SecurityUtil;

@Service
public class MenuServiceImpl implements iMenuService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<AppMenu> getMyMenu() {
        JwtTokenVo jwtTokenVo = SecurityUtil.getSession();
        if (jwtTokenVo.getRoles().isEmpty()) {
            return null;
        }
        String roleId = jwtTokenVo.getRoles().get(0);

        Role role = roleRepository.findById(roleId).orElse(null);

        if (role == null) {
            return null;
        }

        return role.getMenus();
    }

    @Override
    public void updateRoleMenu(String roleId, List<String> menuIds) {
        Role role = roleRepository.findById(roleId).orElse(null);

        if (role == null) {
            return;
        }

        role.getMenus().clear();

        for (String menuId : menuIds) {
            AppMenu menu = new AppMenu();
            menu.setId(menuId);
            role.getMenus().add(menu);
        }

        roleRepository.save(role);
    }

}
