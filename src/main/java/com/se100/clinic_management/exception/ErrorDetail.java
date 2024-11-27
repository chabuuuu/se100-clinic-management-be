package com.se100.clinic_management.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDetail {
    private String code;
    private String message;
}
