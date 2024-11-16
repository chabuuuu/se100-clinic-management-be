package com.se100.clinic_management.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@ToString
public class BaseEntity {
    @Column(updatable = false, name = "create_at")
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(insertable = false, name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "create_by", updatable = false)
    private Integer createdBy;

    @Column(name = "update_by", insertable = false)
    private Integer updatedBy;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;
}
