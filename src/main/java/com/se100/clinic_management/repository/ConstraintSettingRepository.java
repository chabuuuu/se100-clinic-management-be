package com.se100.clinic_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.se100.clinic_management.model.ConstraintSetting;

public interface ConstraintSettingRepository extends JpaRepository<ConstraintSetting, String> {
}
