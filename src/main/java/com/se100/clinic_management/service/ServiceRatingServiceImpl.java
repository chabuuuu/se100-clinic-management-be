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

    ServiceRating rating = optionalRating.orElseThrow(() -> new RuntimeException("Rating not found"));

    if (rating.getDeleteAt() != null) {
      throw new RuntimeException("Rating has been deleted");
    }

    return rating;
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

    // Cập nhật score nếu có giá trị mới
    existingRating.setScore(rating.getScore());

    // Cập nhật feedback nếu có giá trị mới
    if (rating.getFeedback() != null) {
      existingRating.setFeedback(rating.getFeedback());
    }

    // Cập nhật createAt nếu có giá trị mới (nếu cần, nếu không muốn thay đổi thì bỏ
    // qua)
    if (rating.getCreateAt() != null) {
      existingRating.setCreateAt(rating.getCreateAt());
    }

    // Cập nhật updateAt luôn luôn vì đó là thông tin cần thiết khi cập nhật
    existingRating.setUpdateAt(LocalDateTime.now()); // Cập nhật thời gian hiện tại

    // Cập nhật deleteAt nếu có giá trị mới
    if (rating.getDeleteAt() != null) {
      existingRating.setDeleteAt(rating.getDeleteAt());
    }

    // Lưu và trả về đối tượng ServiceRating đã cập nhật
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
