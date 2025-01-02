package com.se100.clinic_management.Interface;

import java.util.List;

import com.se100.clinic_management.model.ConstraintSetting;

public interface iConstraintSettingService {
    ConstraintSetting getByKey(String key);

    void updateConstraint(String key, ConstraintSetting constraintSetting);

    List<ConstraintSetting> getAllConstraint();
}
