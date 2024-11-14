package com.se100.clinic_management.dto.base_format;

public class ResponseVO {

    private String status;
    private String result;
    private ResponseErrorVo error;
    private Object data;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public Object getData() {
        return data;
    }

    public ResponseErrorVo getError() {
        return error;
    }

    public void setError(ResponseErrorVo error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setData(Object data) {
        this.data = data;
    }

}