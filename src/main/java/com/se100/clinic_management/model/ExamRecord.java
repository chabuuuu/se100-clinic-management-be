package com.se100.clinic_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "exam_records")
public class ExamRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "exam_date")
    private Date examDate;

    @Column(name = "numerical_order")
    private Integer numericalOrder;

    @Column(name = "exam_room")
    private String examRoom;

    @Column(name = "symptom")
    private String symptom;

    @Column(name = "diagnose")
    private String diagnose;

    @Column(name = "status")
    private String status;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "doctor_id")
    private int doctorId;

    @Column(name = "service_record_id")
    private Integer serviceRecordId;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @ManyToOne()
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false)
    private Employee doctor;

    @ManyToOne()
    @JoinColumn(name = "service_record_id", insertable = false, updatable = false)
    private ServiceRecord serviceRecord;

    @ManyToOne()
    @JoinColumn(name = "service_type_id", insertable = false, updatable = false)
    private ServiceType serviceType;
}
