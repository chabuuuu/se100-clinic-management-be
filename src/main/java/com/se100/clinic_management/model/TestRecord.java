package com.se100.clinic_management.model;

import com.se100.clinic_management.utils.StringListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "test_records")
public class TestRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "test_date")
    private Date testDate;

    @Column(name = "numerical_order")
    private Integer numericalOrder;

    @Column(columnDefinition = "json", name = "test_artachments")
    @Convert(converter = StringListConverter.class)
    private List<String> testArtachments;

    @Column(name = "diagnose")
    private String diagnose;

    @Column(name = "state")
    private String state;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "technician_id")
    private Integer technicianId;

    @Column(name = "service_type_id")
    private Integer serviceTypeId;

    @Column(name = "service_record_id")
    private Integer serviceRecordId;

    @ManyToOne()
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

    @ManyToOne()
    @JoinColumn(name = "technician_id", insertable = false, updatable = false)
    private Employee technician;

    @ManyToOne()
    @JoinColumn(name = "service_type_id", insertable = false, updatable = false)
    private ServiceType serviceType;
}
