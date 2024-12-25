package com.se100.clinic_management.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "medicine_batches")
public class MedicineBatch {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "amount")
  private int amount;

  @Column(name = "expire_date")
  private LocalDate expireDate;

  @ManyToOne
  @JoinColumn(name = "medicine_id", nullable = false)
  private Medicine medicineId;

  @Column(name = "manufacturer", length = 60)
  private String manufacturer;

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
