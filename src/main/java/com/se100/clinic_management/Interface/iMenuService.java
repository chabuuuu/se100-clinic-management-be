package com.se100.clinic_management.Interface;

import java.util.List;

import com.se100.clinic_management.model.AppMenu;

public interface iMenuService {
    List<AppMenu> getMyMenu();

    void updateRoleMenu(String roleId, List<String> menuIds);

    List<AppMenu> getAllMenu();

    List<AppMenu> getRoleMenu(String roleId);
}
