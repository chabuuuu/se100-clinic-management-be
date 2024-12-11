package com.se100.clinic_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseRes {
    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private Integer createdBy;

    private Integer updatedBy;
}
