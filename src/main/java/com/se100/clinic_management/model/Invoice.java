package com.se100.clinic_management.model;

import jakarta.persistence.*;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "`invoices`")
public class Invoice{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "total")
    private Float total;

    @Column(name = "status")
    private String status;

    @Column(name = "service_record_id")
    private Integer serviceRecordId;

    @Column(name = "receptionist_id")
    private Integer receptionistId;

    @ManyToOne()
    @JoinColumn(name = "service_record_id", insertable = false, updatable = false)
    private ServiceRecord serviceRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receptionist_id", insertable = false, updatable = false)
    private Employee receptionist;

    @Column(updatable = false, name = "create_at")
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(insertable = false, name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Column(name = "update_by", insertable = false)
    private Integer updatedBy;

    @Column(name = "delete_at")
    private LocalDateTime deleteAt;

}
