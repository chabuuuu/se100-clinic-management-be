package com.se100.clinic_management.dto.invoice;

import com.se100.clinic_management.annotations.EnumValidator;
import com.se100.clinic_management.enums.InvoiceStatusEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvoiceStatusReq {
    @EnumValidator(enumClass = InvoiceStatusEnum.class, message = "Invalid status")
    private String status;
}
