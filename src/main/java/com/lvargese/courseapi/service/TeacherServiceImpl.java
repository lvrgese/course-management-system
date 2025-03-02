package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDTO;
import com.lvargese.courseapi.dto.TeacherDTO;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        return null;
    }

    @Override
    public TeacherDTO getTeacherById(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<TeacherDTO> getAllTeachers() {
        return List.of();
    }

    @Override
    public TeacherDTO updateTeacher(Long teacherId, TeacherDTO teacherDTO) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void deleteTeacher(Long teacherId) throws ResourceNotFoundException {

    }

    @Override
    public List<CourseDTO> getCoursesByTeacher(Long teacherId) throws ResourceNotFoundException {
        return List.of();
    }
}
