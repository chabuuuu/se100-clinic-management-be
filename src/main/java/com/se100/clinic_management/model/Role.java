package com.se100.clinic_management.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Id
    private String id;

    @ManyToMany
    @JoinTable(name = "role_menus", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<AppMenu> menus;
}
