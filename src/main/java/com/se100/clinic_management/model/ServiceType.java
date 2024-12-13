package com.se100.clinic_management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "service_types")
public class ServiceType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "service_name", length = 100, nullable = false)
  private String serviceName;

  @Column(name = "price", precision = 10, scale = 2, nullable = false)
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  @Column(name = "type", length = 100)
  private ServiceTypeEnum type;

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

}
