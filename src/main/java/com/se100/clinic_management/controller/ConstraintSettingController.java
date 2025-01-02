package com.se100.clinic_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.se100.clinic_management.Interface.iConstraintSettingService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.model.ConstraintSetting;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("api/constraint-settings")
@RequiredArgsConstructor
public class ConstraintSettingController {
    @Autowired
    private final iConstraintSettingService constraintSettingService;

    @GetMapping("")
    public ResponseEntity<ResponseVO> getAll() {
        List<ConstraintSetting> result = constraintSettingService.getAllConstraint();

        return ResponseEntityGenerator.find(result);
    }

    @PutMapping("{key}")
    public ResponseEntity<ResponseVO> updateByKey(@PathVariable String key,
            @RequestBody ConstraintSetting constraintSetting) {
        constraintSettingService.updateConstraint(key, constraintSetting);

        return ResponseEntityGenerator.updateFormat("Update success");
    }

    @GetMapping("{key}")
    public ResponseEntity<ResponseVO> getMethodName(@PathVariable String key) {
        ConstraintSetting result = constraintSettingService.getByKey(key);

        return ResponseEntityGenerator.find(result);
    }

}
