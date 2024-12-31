package com.se100.clinic_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_menu")
public class AppMenu {
    @Id
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
