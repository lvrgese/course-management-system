package com.lvargese.courseapi.service;
import com.lvargese.courseapi.dto.TeacherDTO;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.ResourceNotFoundException;

import java.util.List;

public interface TeacherService {
    TeacherDTO createTeacher(Teacher teacher);
    TeacherDTO getTeacherById(Long id) throws ResourceNotFoundException;
    List<Teacher> getAllTeachers();
    List<Course> getCoursesByTeacher(Long teacherId);
    TeacherDTO deleteTeacher(Long teacherId);
}
