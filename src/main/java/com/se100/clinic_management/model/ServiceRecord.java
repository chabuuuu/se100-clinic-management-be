package com.se100.clinic_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "service_records")
public class ServiceRecord extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "receptionist_id")
    private int receptionistId;

    @ManyToOne()
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "receptionist_id", insertable = false, updatable = false)
    private Employee receptionist;
}
