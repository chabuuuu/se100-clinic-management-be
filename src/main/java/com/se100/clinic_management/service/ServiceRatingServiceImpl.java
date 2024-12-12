package com.se100.clinic_management.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iServiceRatingService;
import com.se100.clinic_management.model.ServiceRating;
import com.se100.clinic_management.repository.ServiceRatingRepository;
import com.se100.clinic_management.specification.ServiceRatingSpecification;

@Service
public class ServiceRatingServiceImpl implements iServiceRatingService {

  @Autowired
  private ServiceRatingRepository serviceRatingRepository;

  @Override
  public ServiceRating getRatingById(int id) {
    Optional<ServiceRating> optionalRating = serviceRatingRepository.findById(id);
    return optionalRating.orElseThrow(() -> new RuntimeException("Rating not found"));
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
  public ServiceRating updateRating(int id, ServiceRating rating) {
    ServiceRating existingRating = getRatingById(id);
    existingRating.setScore(rating.getScore());
    existingRating.setFeedback(rating.getFeedback());
    existingRating.setCreateAt(rating.getCreateAt());
    existingRating.setUpdateAt(LocalDateTime.now());
    existingRating.setDeleteAt(rating.getDeleteAt());
    return serviceRatingRepository.save(existingRating);
  }

  @Override
  public void deleteRating(int id) {
    ServiceRating existingRating = getRatingById(id);
    existingRating.setDeleteAt(LocalDateTime.now());
    serviceRatingRepository.save(existingRating);
  }

  @Override
  public Page<ServiceRating> getRatings(Integer minScore, Integer maxScore, Integer patientId,
      LocalDateTime createdAfter, LocalDateTime createdBefore, Pageable pageable) {
    Specification<ServiceRating> spec = ServiceRatingSpecification.filter(minScore, maxScore, patientId,
        createdAfter, createdBefore);
    return serviceRatingRepository.findAll(spec, pageable);
  }

}
