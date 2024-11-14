package com.se100.clinic_management.constants.error;

import com.fasterxml.jackson.annotation.JsonValue;

public interface Type<T> {
    @JsonValue
    T getValue();

    String getDescription();
}
