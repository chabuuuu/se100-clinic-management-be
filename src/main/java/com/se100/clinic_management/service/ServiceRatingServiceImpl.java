package com.se100.clinic_management.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iServiceRatingService;
import com.se100.clinic_management.dto.ratings.PatientDto;
import com.se100.clinic_management.dto.ratings.ServiceRatingDto;
import com.se100.clinic_management.dto.ratings.ServiceRecordDto;
import com.se100.clinic_management.model.ServiceRating;
import com.se100.clinic_management.repository.ServiceRatingRepository;
import com.se100.clinic_management.specification.ServiceRatingSpecification;

@Service
public class ServiceRatingServiceImpl implements iServiceRatingService {

  @Autowired
  private ServiceRatingRepository serviceRatingRepository;

  @Override
  public ServiceRatingDto getRatingById(int id) {
    Optional<ServiceRating> optionalRating = serviceRatingRepository.findById(id);

    ServiceRating rating = optionalRating.orElseThrow(() -> new RuntimeException("Rating not found"));

    if (rating.getDeleteAt() != null) {
      throw new RuntimeException("Rating has been deleted");
    }

    // Chuyển đổi sang Dto
    PatientDto patientDto = new PatientDto(
        rating.getPatient().getId(),
        rating.getPatient().getFullname());

    ServiceRecordDto serviceRecordDto = new ServiceRecordDto(
        rating.getServiceRecord().getId());

    return new ServiceRatingDto(
        rating.getId(),
        rating.getScore(),
        rating.getFeedback(),
        patientDto,
        serviceRecordDto, rating.getCreateAt(),
        rating.getUpdateAt(),
        rating.getDeleteAt());
  }

  @Override
  public ServiceRating createRating(ServiceRating rating) {
    // check if score is from 1 to 5
    if (rating.getScore() < 1 || rating.getScore() > 5) {
      throw new IllegalArgumentException("Invalid score: " + rating.getScore());
    }
    rating.setCreateAt(LocalDateTime.now());
    return serviceRatingRepository.save(rating);
  }

  @Override
  public ServiceRatingDto updateRating(int id, ServiceRating rating) {
    ServiceRating existingRating = serviceRatingRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Rating not found"));

    // Cập nhật score nếu có giá trị mới
    existingRating.setScore(rating.getScore());

    // Cập nhật feedback nếu có giá trị mới
    if (rating.getFeedback() != null) {
      existingRating.setFeedback(rating.getFeedback());
    }

    // Cập nhật createAt nếu có giá trị mới (nếu cần)
    if (rating.getCreateAt() != null) {
      existingRating.setCreateAt(rating.getCreateAt());
    }

    // Cập nhật updateAt luôn luôn vì đó là thông tin cần thiết khi cập nhật
    existingRating.setUpdateAt(LocalDateTime.now());

    // Cập nhật deleteAt nếu có giá trị mới
    if (rating.getDeleteAt() != null) {
      existingRating.setDeleteAt(rating.getDeleteAt());
    }

    // Lưu và trả về Dto
    ServiceRating updatedRating = serviceRatingRepository.save(existingRating);

    // Chuyển đổi sang Dto
    PatientDto patientDto = new PatientDto(
        updatedRating.getPatient().getId(),
        updatedRating.getPatient().getFullname());

    ServiceRecordDto serviceRecordDto = new ServiceRecordDto(
        updatedRating.getServiceRecord().getId());

    return new ServiceRatingDto(
        updatedRating.getId(),
        updatedRating.getScore(),
        updatedRating.getFeedback(),
        patientDto,
        serviceRecordDto,
        updatedRating.getCreateAt(),
        updatedRating.getUpdateAt(),
        updatedRating.getDeleteAt()

    );
  }

  @Override
  public void deleteRating(int id) {
    ServiceRating existingRating = serviceRatingRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Rating not found"));

    existingRating.setDeleteAt(LocalDateTime.now());
    serviceRatingRepository.save(existingRating);
  }

  @Override
  public Page<ServiceRatingDto> getRatings(
      Integer minScore,
      Integer maxScore,
      Integer patientId,
      LocalDateTime createdAfter,
      LocalDateTime createdBefore,
      Pageable pageable) {

    Specification<ServiceRating> spec = ServiceRatingSpecification.filter(
        minScore, maxScore, patientId, createdAfter, createdBefore);

    Page<ServiceRating> ratings = serviceRatingRepository.findAll(spec, pageable);

    // Chuyển đổi các entity sang Dto
    return ratings.map(rating -> {
      PatientDto patientDto = new PatientDto(
          rating.getPatient().getId(),
          rating.getPatient().getFullname());

      ServiceRecordDto serviceRecordDto = new ServiceRecordDto(
          rating.getServiceRecord().getId());

      return new ServiceRatingDto(
          rating.getId(),
          rating.getScore(),
          rating.getFeedback(),
          patientDto,
          serviceRecordDto,
          rating.getCreateAt(),
          rating.getUpdateAt(),
          rating.getDeleteAt());
    });
  }

}
