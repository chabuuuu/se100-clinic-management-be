package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iEmployeeService;
import com.se100.clinic_management.Interface.iInvoiceService;
import com.se100.clinic_management.constants.ExamRecordStatus;
import com.se100.clinic_management.constants.InvoiceStatus;
import com.se100.clinic_management.constants.RoleConst;
import com.se100.clinic_management.constants.prescription.PrescriptionStatus;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.dto.PatientDto;
import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.invoice.CreateInvoiceReq;
import com.se100.clinic_management.dto.invoice.InvoiceDetailDto;
import com.se100.clinic_management.dto.invoice.InvoiceDto;
import com.se100.clinic_management.dto.invoice.UpdateInvoiceStatusReq;
import com.se100.clinic_management.enums.ExamRecordStatusEnum;
import com.se100.clinic_management.enums.InvoiceStatusEnum;
import com.se100.clinic_management.enums.TestRecordStatusEnum;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.Invoice;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.model.ServiceRecord;
import com.se100.clinic_management.repository.EmployeeRepository;
import com.se100.clinic_management.repository.ExamRecordRepository;
import com.se100.clinic_management.repository.InvoiceRepository;
import com.se100.clinic_management.repository.PrescriptionRepository;
import com.se100.clinic_management.repository.ServiceRecordRepository;
import com.se100.clinic_management.repository.TestRecordRepository;
import com.se100.clinic_management.specification.InvoiceSpecification;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.se100.clinic_management.Interface.iServiceRecordService;

import java.util.Date;

@Service
public class InvoiceServiceImpl implements iInvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private iServiceRecordService serviceRecordService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    @Autowired
    private ExamRecordRepository examRecordRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private TestRecordRepository testRecordRepository;

    @SneakyThrows
    @Override
    public void createInvoice(CreateInvoiceReq createInvoiceReq) {
        // Only receptionist can create invoice
        JwtTokenVo jwtTokenVo = SecurityUtil.getSession();
        if (!jwtTokenVo.getRoles().contains(RoleConst.RECEPTIONIST)) {
            throw new BaseError("UNAUTHORIZED", "Only receptionists are authorized to create invoice",
                    HttpStatus.FORBIDDEN);
        }

        Invoice invoice = new Invoice();
        invoice.setServiceRecordId(createInvoiceReq.getServiceRecordId());

        Employee receptionist = employeeRepository.findById(jwtTokenVo.getUserId()).orElse(null);

        if (receptionist == null) {
            throw new BaseError("RECEPTIONIST_NOT_FOUND", "Receptionist not found", HttpStatus.BAD_REQUEST);
        }

        invoice.setReceptionistId(5);

        Float total = serviceRecordService.getTotalServiceFee(createInvoiceReq.getServiceRecordId());

        invoice.setTotal(total);
        invoice.setStatus("UNPAID");

        invoiceRepository.insertInvoice(invoice.getReceptionistId(), invoice.getTotal(), invoice.getServiceRecordId(),
                invoice.getStatus());
    }

    @Override
    public Page<InvoiceDto> getInvoices(String patientName, String receptionistName, Date startDate, Date endDate,
            Pageable pageable) {
        Specification<Invoice> specification = InvoiceSpecification.filter(patientName, receptionistName, startDate,
                endDate);
        Page<Invoice> invoices = invoiceRepository.findAll(specification, pageable);

        return invoices.map(invoice -> {
            InvoiceDto invoiceDto = new InvoiceDto();
            invoiceDto.setId(invoice.getId());
            invoiceDto.setTotal(invoice.getTotal());
            invoiceDto.setStatus(invoice.getStatus());
            invoiceDto.setReceptionistId(invoice.getReceptionistId());
            invoiceDto.setServiceRecordId(invoice.getServiceRecordId());
            invoiceDto.setCreateAt(invoice.getCreateAt());
            invoiceDto.setUpdateAt(invoice.getUpdateAt());
            invoiceDto.setUpdatedBy(invoice.getUpdatedBy());

            ModelMapper modelMapper = new ModelMapper();

            EmployeeProfileDTO receptionist = new EmployeeProfileDTO();
            Employee employee = employeeRepository.findById(invoice.getReceptionistId()).orElse(null);
            if (employee != null) {
                receptionist = modelMapper.map(employee, EmployeeProfileDTO.class);
            }
            invoiceDto.setReceptionist(receptionist);

            ServiceRecord serviceRecord = serviceRecordRepository.findById(invoice.getServiceRecordId()).orElse(null);

            if (serviceRecord != null) {
                Patient patient = serviceRecord.getPatient();
                if (patient != null) {
                    invoiceDto.setPatient(modelMapper.map(patient, PatientDto.class));
                }
            }
            return invoiceDto;
        });
    }

    @Override
    public InvoiceDetailDto getInvoiceDetail(int invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null) {
            return null;
        }

        InvoiceDetailDto invoiceDetailDto = new InvoiceDetailDto();
        invoiceDetailDto.setId(invoice.getId());
        invoiceDetailDto.setTotal(invoice.getTotal());
        invoiceDetailDto.setStatus(invoice.getStatus());
        invoiceDetailDto.setReceptionistId(invoice.getReceptionistId());
        invoiceDetailDto.setServiceRecordId(invoice.getServiceRecordId());

        ServiceRecordDetailDto serviceRecord = serviceRecordService
                .getServiceRecordDetail(invoice.getServiceRecordId());
        invoiceDetailDto.setServiceRecord(serviceRecord);

        EmployeeProfileDTO receptionist = new EmployeeProfileDTO();
        Employee employee = employeeRepository.findById(invoice.getReceptionistId()).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        if (employee != null) {
            receptionist = modelMapper.map(employee, EmployeeProfileDTO.class);
        }

        invoiceDetailDto.setReceptionist(receptionist);

        invoiceDetailDto.setCreateAt(invoice.getCreateAt());
        invoiceDetailDto.setUpdateAt(invoice.getUpdateAt());
        invoiceDetailDto.setUpdatedBy(invoice.getUpdatedBy());

        return invoiceDetailDto;
    }

    @Override
    @SneakyThrows
    public void updateInvoiceStatus(int invoiceId, UpdateInvoiceStatusReq updateInvoiceStatusReq) {
        Invoice invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice == null) {
            throw new BaseError("INVOICE_NOT_FOUND", "Invoice not found", HttpStatus.BAD_REQUEST);
        }
        invoice.setStatus(updateInvoiceStatusReq.getStatus());

        if (invoice.getStatus().equals(InvoiceStatusEnum.PAID.toString())) {
            ServiceRecord serviceRecord = invoice.getServiceRecord();

            // Update prescription status
            serviceRecord.getPrescriptions().forEach(prescription -> {
                prescription.setStatus(PrescriptionStatus.PAID.toString());
                prescriptionRepository.save(prescription);
            });

            // Update exam record status
            serviceRecord.getExamRecords().forEach(examRecord -> {
                examRecord.setStatus(ExamRecordStatusEnum.FINISH.toString());
                examRecordRepository.save(examRecord);
            });

            // Update test record
            serviceRecord.getTestRecords().forEach(testRecord -> {
                testRecord.setState(TestRecordStatusEnum.PAID.toString());
                testRecordRepository.save(testRecord);
            });
        }

        invoiceRepository.save(invoice);
        return;
    }
}
