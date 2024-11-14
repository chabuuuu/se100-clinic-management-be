package com.se100.clinic_management.exception;

import com.se100.clinic_management.constants.error.BizErrorCode;
import com.se100.clinic_management.constants.error.ErrorCodeType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class BaseError extends Exception {

    private static final long serialVersionUID = 4127513561428645333L;

    @Getter
    private ErrorCodeType error;
    @Setter
    @Getter
    private ErrorDetail errorDetail;
    @Getter
    private String description;
    @Getter
    @Setter
    private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public BaseError() {
        this(BizErrorCode.E0001);
    }

    public BaseError(String description) {
        super(description);
        error = BizErrorCode.E0001;
        this.description = description;
    }

    public BaseError(String code, String message, HttpStatus httpStatus) {
        this.errorDetail = new ErrorDetail();
        this.errorDetail.setMessage(message);
        this.errorDetail.setCode(code);
        this.httpStatus = httpStatus;
    }

    public BaseError(ErrorCodeType bizError) {
        super(bizError.getDescription());
        this.error = bizError;
    }

    public BaseError(ErrorCodeType bizError, String description) {
        super(description);

        this.error = bizError;
        this.description = description;
    }


}
