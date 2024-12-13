package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.ServiceRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRatingRepository
    extends JpaRepository<ServiceRating, Integer>, JpaSpecificationExecutor<ServiceRating> {

}
