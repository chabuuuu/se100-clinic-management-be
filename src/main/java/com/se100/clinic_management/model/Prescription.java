package com.se100.clinic_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacist_id")
    private Employee pharmacist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_record_id")
    private ExamRecord examRecord;
}
