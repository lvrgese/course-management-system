package com.lvargese.courseapi.service;
import com.lvargese.courseapi.dto.CourseDTO;
import com.lvargese.courseapi.dto.TeacherDTO;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.ResourceNotFoundException;

import java.util.List;

public interface TeacherService {
    TeacherDTO createTeacher(TeacherDTO teacherDTO);
    TeacherDTO getTeacherById(Long id) throws ResourceNotFoundException;
    List<TeacherDTO> getAllTeachers();
    TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO) throws ResourceNotFoundException;
    void deleteTeacher(Long teacherId) throws ResourceNotFoundException;
    List<CourseDTO> getCoursesByTeacher(Long teacherId) throws ResourceNotFoundException;
}
