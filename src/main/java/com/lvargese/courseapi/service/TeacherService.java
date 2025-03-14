package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.utils.PagedResponse;

import java.util.List;

public interface TeacherService {
    TeacherDto createTeacher(TeacherDto teacherDto);
    TeacherDto getTeacherById(Long id);
    PagedResponse<TeacherDto> getAllTeachers(Integer pageNumber, Integer size, String sortBy, String dir);
    TeacherDto updateTeacherById(Long id, TeacherDto dto);
    void deleteTeacherById(Long id);
    List<CourseDto> getCoursesByTeacherId(Long id);
}
