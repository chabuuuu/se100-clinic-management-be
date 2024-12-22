package com.se100.clinic_management.model;


import com.se100.clinic_management.dto.PrescriptionDetailId;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@IdClass(PrescriptionDetailId.class)
@Table(name = "prescription_details")
public class PrescriptionDetail extends BaseEntity{
    @Id
    @Column(name = "prescription_id")
    private int prescriptionId;

    @Id
    @Column(name = "medicine_id")
    private int medicineId;

    @Column(name = "dosage")
    private String dosage;

    @Column(name = "amount")
    private int amount;

    @Column(name = "notes")
    private String notes;

    @ManyToOne()
    @JoinColumn(name = "medicine_id", insertable = false, updatable = false)
    private Medicine medicine;

    @ManyToOne()
    @JoinColumn(name = "prescription_id", insertable = false, updatable = false)
    private Prescription prescription;
}
