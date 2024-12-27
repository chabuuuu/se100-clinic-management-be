package com.se100.clinic_management.dto.invoice;

import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDto {
    private int id;

    private Float total;

    private String status;

    private Integer serviceRecordId;


    private Integer receptionistId;


    private ServiceRecordDetailDto serviceRecord;


    private EmployeeProfileDTO receptionist;


    private LocalDateTime createAt;


    private LocalDateTime updateAt;

    private Integer updatedBy;

}
