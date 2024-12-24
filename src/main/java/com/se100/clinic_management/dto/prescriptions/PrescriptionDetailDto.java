package com.se100.clinic_management.dto.prescriptions;

import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.Medicine;
import com.se100.clinic_management.model.ServiceType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDetailDto {
    private Integer id;

    private String status;

    private Integer serviceRecordId;

    private ServiceType serviceType;

    private PharmacistDto pharmacist;

    private PatientDto patient;

    private Integer examRecordId;

    private List<MedicineDetailDto> prescriptionDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatientDto {
        private int id;
        private String fullname;
        private String phoneNumber;
        private LocalDate birthday;
        private boolean gender;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PharmacistDto {
        private int id;
        private String username;
        private String fullname;
        private String email;
        private String role;
        private String shift;
        private LocalDate dob;
        private String phoneNumber;
        private String department;
        private boolean gender;
        private String avatar;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MedicineDetailDto {
        private Medicine medicine;

        private Integer amount;

        private String notes;

        private String dosage; //Liều lượng
    }

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer createdBy;

    private Integer updatedBy;
}
