package com.se100.clinic_management.exception;

import com.se100.clinic_management.constants.error.BizErrorCode;
import com.se100.clinic_management.dto.base_format.ResponseErrorVo;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.base_format.ResponseVOBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String validationErrors = new String();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        for (ObjectError error : validationErrorList) {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors += fieldName + ": " + validationMsg + ", ";
        }
        return new ResponseEntity<>(new ResponseVOBuilder().fail().error(new ResponseErrorVo("VALIDATE_ERR", validationErrors.toString()), "400").build(), HttpStatus.BAD_REQUEST);

    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> responseException(Exception ex) {
        log.error("Exception {}", ex.getMessage(), ex);
        return new ResponseEntity<>(new ResponseVOBuilder().fail().error(new ResponseErrorVo(BizErrorCode.E0001.getValue(), ex.getLocalizedMessage()), "500").build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BaseError.class)
    public ResponseEntity<ResponseVO> responseBizException(BaseError ex) {
        log.error("BizException {}", ex.getMessage(), ex);
        String code = ex.getErrorDetail().getCode();
        String message = ex.getErrorDetail().getMessage();
        ResponseErrorVo responseErrorVo = new ResponseErrorVo(code, message);
        String status = String.valueOf(ex.getHttpStatus().value());
        return new ResponseEntity<>(new ResponseVOBuilder().fail().error(responseErrorVo, status).build(), ex.getHttpStatus());
    }
}
