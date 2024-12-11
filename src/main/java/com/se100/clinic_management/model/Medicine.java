package com.se100.clinic_management.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "medicines")
public class Medicine {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createAt;

  @Column(name = "update_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateAt;

  @Column(name = "create_by", length = 70)
  private String createBy;

  @Column(name = "update_by", length = 70)
  private String updateBy;

  @Column(name = "delete_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime deleteAt;

  @Column(name = "ingredient", columnDefinition = "TEXT")
  private String ingredient;

  @Column(name = "dosage_form", length = 100)
  private String dosageForm;

  @Column(name = "price", precision = 10, scale = 2)
  private double price;
}
