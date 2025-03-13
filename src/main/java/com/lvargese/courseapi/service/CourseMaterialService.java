package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseMaterialDto;

public interface CourseMaterialService {
    CourseMaterialDto createCourseMaterial(CourseMaterialDto dto);
    CourseMaterialDto getCourseMaterialById(Long id);
    CourseMaterialDto updateMaterialById(Long id,CourseMaterialDto dto);
    void deleteMaterialById(Long id);
}
