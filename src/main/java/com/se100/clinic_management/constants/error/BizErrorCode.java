package com.se100.clinic_management.constants.error;

public enum BizErrorCode implements ErrorCodeType {

    /**
     * Error General exception.
     */
    E0001("E0001", "General exception error."),
    E0002("E0002", "Record not found."),;

    final String value;
    final String description;

    BizErrorCode(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
