package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.repository.CourseRepository;
import com.lvargese.courseapi.repository.StudentRepository;
import com.lvargese.courseapi.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;

    @Captor
    ArgumentCaptor<Course> courseArgumentCaptor;

    @InjectMocks
    private CourseServiceImpl courseService;
    Teacher teacher = Teacher.builder()
            .teacherId(1L)
            .firstName("Lily")
            .email("lilyjames@gmail.com")
            .build();
    CourseMaterial material = CourseMaterial.builder()
            .courseMaterialId(1L)
            .url("http://localhost:8080/1")
            .build();
    Course course = Course.builder()
            .courseId(1L)
            .title("AI")
            .credit(5)
            .teacher(teacher)
            .courseMaterial(material)
            .students(new ArrayList<>())
            .build();

    CourseMaterialDto materialDto = CourseMaterialDto.builder()
            .courseMaterialId(1L)
            .url("http://localhost:8080/1")
            .courseId(1L)
            .build();
    CourseDto courseDto = CourseDto.builder()
            .courseId(1L)
            .title("AI")
            .credit(5)
            .teacherId(1L)
            .courseMaterialDto(materialDto)
            .build();

    @Test
    public void createCourse_ValidCourseDto_ShouldReturnCourseDto(){

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDto responseDto = courseService.createCourse(courseDto);

        verify(courseRepository).save(courseArgumentCaptor.capture());
        Course argCourse = courseArgumentCaptor.getValue();

        assertNotNull(argCourse);
        assertEquals(courseDto.getTitle(),argCourse.getTitle());
        assertEquals(courseDto.getCredit(),argCourse.getCredit());
        assertEquals(courseDto.getTeacherId(),argCourse.getTeacher().getTeacherId());
        assertEquals(courseDto.getCourseMaterialDto().getUrl(),argCourse.getCourseMaterial().getUrl());

        verify(teacherRepository,times(1)).findById(1L);
        verify(courseRepository,times(1)).save(argCourse);
    }
}