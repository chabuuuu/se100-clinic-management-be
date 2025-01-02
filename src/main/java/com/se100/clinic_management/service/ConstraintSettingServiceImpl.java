package com.se100.clinic_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.se100.clinic_management.Interface.iConstraintSettingService;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.ConstraintSetting;
import com.se100.clinic_management.repository.ConstraintSettingRepository;

import lombok.SneakyThrows;

@Service
public class ConstraintSettingServiceImpl implements iConstraintSettingService {

    @Autowired
    private ConstraintSettingRepository constraintSettingRepository;

    @Override
    public ConstraintSetting getByKey(String key) {
        return constraintSettingRepository.findById(key).orElse(null);
    }

    @SneakyThrows
    @Override
    public void updateConstraint(String key, ConstraintSetting constraintSetting) {
        ConstraintSetting constraintSettingToUpdate = constraintSettingRepository.findById(key).orElse(null);

        if (constraintSettingToUpdate == null) {
            throw new BaseError("CONSTRAINT_NOT_FOUND", "Constraint not found", HttpStatus.NOT_FOUND);
        }

        constraintSettingToUpdate.setMaxValue(constraintSetting.getMaxValue());
        constraintSettingToUpdate.setMinValue(constraintSetting.getMinValue());

        constraintSettingRepository.save(constraintSettingToUpdate);
    }

    @Override
    public List<ConstraintSetting> getAllConstraint() {
        return constraintSettingRepository.findAll();
    }

}
