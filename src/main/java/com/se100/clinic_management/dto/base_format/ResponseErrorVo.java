package com.se100.clinic_management.dto.base_format;
import lombok.Getter;
import lombok.Setter;


public class ResponseErrorVo {
    @Getter
    @Setter()
    private String code;
    @Setter
    @Getter
    private String message;
    public ResponseErrorVo(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
