package com.se100.clinic_management.model;

import jakarta.persistence.*;
import lombok.Data;

import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "prescriptions")
public class Prescription extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "status")
    private String status;

    @Column(name = "service_record_id")
    private Integer serviceRecordId;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacist_id")
    private Employee pharmacist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_record_id")
    private ExamRecord examRecord;

    @Column(name = "total")
    private Float total;

    @ManyToOne()
    @JoinColumn(name = "service_record_id", insertable = false, updatable = false)
    private ServiceRecord serviceRecord;

    @ManyToOne()
    @JoinColumn(name = "service_type_id", insertable = false, updatable = false)
    private ServiceType serviceType;

    @OneToMany(mappedBy = "prescription", fetch = FetchType.LAZY)
    private List<PrescriptionDetail> prescriptionDetails;
}
