package com.se100.clinic_management.model;

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
@Table(name = "service_ratings")
public class ServiceRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "score", nullable = false)
  private int score;

  @Column(name = "feedback", columnDefinition = "TEXT", nullable = false)
  private String feedback;

  @ManyToOne()
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @ManyToOne()
  @JoinColumn(name = "service_record_id", nullable = false)
  private ServiceRecord serviceRecord;

  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createAt;

  @Column(name = "update_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updateAt;

  @Column(name = "delete_at")
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime deleteAt;
}
