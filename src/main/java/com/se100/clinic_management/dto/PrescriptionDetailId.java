package com.se100.clinic_management.dto;

import java.io.Serializable;
import java.util.Objects;

public class PrescriptionDetailId implements Serializable {
    private int prescriptionId;
    private int medicineId;

    // Default constructor
    public PrescriptionDetailId() {}

    // Parameterized constructor
    public PrescriptionDetailId(int prescriptionId, int medicineId) {
        this.prescriptionId = prescriptionId;
        this.medicineId = medicineId;
    }

    // Getters and setters
    public int getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    // Override equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrescriptionDetailId that = (PrescriptionDetailId) o;
        return prescriptionId == that.prescriptionId && medicineId == that.medicineId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionId, medicineId);
    }
}
