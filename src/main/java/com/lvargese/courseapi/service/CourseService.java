package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.StudentDto;

import java.util.List;


public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    CourseDto getCourseById(Long id);
    List<CourseDto> getAllCourses();
    CourseDto updateCourseById(Long id,CourseDto courseDto);
    void deleteCourseById(Long id);
    List<StudentDto> getStudentsByCourseId(Long id);
    CourseDto enrollStudentInCourse(Long courseId,Long studentId);
}
