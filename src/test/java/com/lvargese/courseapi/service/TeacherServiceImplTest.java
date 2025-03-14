package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.InvalidRequestException;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.TeacherRepository;
import com.lvargese.courseapi.utils.PagedResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private  TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Captor
    ArgumentCaptor<Teacher> teacherCaptor;

    TeacherDto teacherDto = TeacherDto.builder()
            .teacherId(1L)
            .firstName("Jessy")
            .lastName("")
            .email("jessy@example.com")
            .build();
    Teacher teacher = Teacher.builder()
            .teacherId(1L)
            .firstName("Jessy")
            .lastName("")
            .email("jessy@example.com")
            .build();

    @Test
    public void createTeacher_ValidTeacherDto_ShouldReturnTeacherDto() {
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDto dto = teacherService.createTeacher(teacherDto);

        assertNotNull(dto);
        assertEquals(dto.getFirstName(),teacher.getFirstName());
        assertEquals(dto.getEmail(),teacher.getEmail());
        assertEquals(dto.getLastName(),teacher.getLastName());
        verify(teacherRepository,times(1)).save(any(Teacher.class));
    }

    @Test
    public void getTeacherById_TeacherExists_ShouldReturnTeacherDto(){
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        TeacherDto dto = teacherService.getTeacherById(1L);
        assertNotNull(dto);
        assertEquals(dto.getFirstName(),teacher.getFirstName());
        assertEquals(dto.getEmail(),teacher.getEmail());
        assertEquals(dto.getLastName(),teacher.getLastName());
        verify(teacherRepository,times(1)).findById(1L);
    }

    @Test
    public void getTeacherById_TeacherNotExists_ShouldThrowResourceNotFoundException() {
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> teacherService.getTeacherById(1L));
    }

    @Test
    public void getAllTeachers_TeachersExists_ShouldReturnPagedResponse(){
        Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Direction.ASC, "firstName"));
        List<Teacher> teachers = List.of(teacher); // Two mock teachers
        Page<Teacher> teacherPage = new PageImpl<>(teachers, pageable, teachers.size());

        when(teacherRepository.findAll(any(Pageable.class))).thenReturn(teacherPage);

        PagedResponse<TeacherDto> response = teacherService.getAllTeachers(0,2,"firstName","asc");

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertEquals(1,response.getTotalElements());
        assertEquals(1,response.getTotalPages());
        verify(teacherRepository,times(1)).findAll(any(Pageable.class));

    }

    @Test
    public void getAllTeachers_TeachersNotExists_ShouldThrowInvalidRequestException(){
        assertThrows(InvalidRequestException.class, ()->teacherService.getAllTeachers(0,2,"firstName","ascend"));

        verifyNoInteractions(teacherRepository);
    }

    @Test
    public void updateTeacherById_TeacherExists_ShouldReturnUpdatedTeacherDto(){
        TeacherDto dtoToUpdate = TeacherDto.builder()
                .teacherId(1L)
                .firstName("Jessy")
                .lastName("Kuriakose")
                .email("jessy123@example.com")
                .build();

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDto dto = teacherService.updateTeacherById(1L,dtoToUpdate);

        verify(teacherRepository).save(teacherCaptor.capture());
        Teacher updatedTeacher = teacherCaptor.getValue();

        assertNotNull(updatedTeacher);
        assertEquals(dtoToUpdate.getFirstName(),updatedTeacher.getFirstName());
        assertEquals(dtoToUpdate.getLastName(),updatedTeacher.getLastName());
        assertEquals(dtoToUpdate.getEmail(),updatedTeacher.getEmail());

        verify(teacherRepository,times(1)).save(any(Teacher.class));
    }

    @Test
    public void updateTeacherById_TeacherNotExists_ShouldThrowResourceNotFoundException(){
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->teacherService.updateTeacherById(1L,TeacherDto.builder().build()));
        verify(teacherRepository,never()).save(any(Teacher.class));
    }

    @Test
    public void deleteTeacherById_TeacherExists_ShouldDeleteTeacher(){
        when(teacherRepository.existsById(1L)).thenReturn(true);
        teacherService.deleteTeacherById(1L);
        verify(teacherRepository,times(1)).existsById(1L);
        verify(teacherRepository,times(1)).deleteById(1L);
    }

    @Test
    public void deleteTeacherById_TeacherNotExists_ShouldThrowResourceNotFoundException(){
        when(teacherRepository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class,()->teacherService.deleteTeacherById(1L));
        verify(teacherRepository,never()).deleteById(1L);
    }

    @Test
    public void getCoursesByTeacherId_TeacherExists_ShouldReturnCourseDtoList(){
        Course course = Course.builder()
                .courseId(1L)
                .title("AI")
                .credit(5)
                .courseMaterial(
                        CourseMaterial.builder()
                                .courseMaterialId(1L)
                                .url("http://github.com")
                                .build()
                )
                .teacher(teacher)
                .build();
        teacher.setCourses(List.of(course));

        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.of(teacher));
        List<CourseDto> courseDtoList = teacherService.getCoursesByTeacherId(1L);

        assertNotNull(courseDtoList);
        assertFalse(courseDtoList.isEmpty());
        assertEquals(1,courseDtoList.size());
        CourseDto dto = courseDtoList.getFirst();
        assertEquals("AI", dto.getTitle());
        assertEquals(5, dto.getCredit());
        assertEquals(1L, dto.getCourseId());
        assertNotNull(dto.getCourseMaterialDto());
        assertEquals("http://github.com", dto.getCourseMaterialDto().getUrl());

        verify(teacherRepository,times(1)).findById(1L);
    }

    @Test
    public void getCoursesByTeacherId_TeacherNotExists_ShouldThrowResourceNotFoundException(){
        when(teacherRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,()->teacherService.getCoursesByTeacherId(1L));
    }
}