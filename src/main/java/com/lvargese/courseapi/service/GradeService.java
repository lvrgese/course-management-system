package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.GradeDto;
import com.lvargese.courseapi.utils.PagedResponse;

import java.util.List;

public interface GradeService {
    GradeDto assignGrade(GradeDto dto,Long studentId,Long courseId);
    GradeDto getGradeById(Long id);
    GradeDto getGradeByStudentAndCourse(Long studentId,Long courseId);
    List<GradeDto> getAllGradesByStudentId(Long studentId);
    List<GradeDto> getGradesByCourseId(Long courseId);
    GradeDto updateGradeById(Long id, GradeDto gradeDto);
    void deleteGradeById(Long id);
}
