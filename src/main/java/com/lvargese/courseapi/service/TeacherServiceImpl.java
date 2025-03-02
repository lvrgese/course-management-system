package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.TeacherDTO;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public TeacherDTO createTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public TeacherDTO getTeacherById(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return List.of();
    }

    @Override
    public List<Course> getCoursesByTeacher(Long teacherId) {
        return List.of();
    }

    @Override
    public TeacherDTO deleteTeacher(Long teacherId) {
        return null;
    }
}
