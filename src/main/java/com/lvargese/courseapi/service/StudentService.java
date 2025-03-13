package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.StudentDto;

import java.util.List;

public interface StudentService {

    StudentDto createStudent(StudentDto dto);
    StudentDto getStudentById(Long id);
    List<StudentDto> getAllStudents();
    StudentDto updateStudentById(Long id, StudentDto studentDto);
    void deleteStudentById(Long id);
    List<CourseDto> getCoursesEnrolledByStudentById(Long id);
}
