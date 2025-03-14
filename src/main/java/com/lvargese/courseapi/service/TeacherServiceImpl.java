package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.InvalidRequestException;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.TeacherRepository;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDto createTeacher(TeacherDto dto) {
        Teacher teacher= Teacher.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .build();
        Teacher savedTeacher= teacherRepository.save(teacher);
        return getDtoFromTeacher(savedTeacher);
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with Id: "+ id));
        return getDtoFromTeacher(teacher);
    }

    @Override
    public PagedResponse<TeacherDto> getAllTeachers(Integer pageNumber, Integer size, String sortBy, String dir) {

        Sort.Direction direction;
        try{
            direction =Sort.Direction.fromString(dir);
        }
        catch (IllegalArgumentException e){
            throw new InvalidRequestException("Invalid sorting direction. Use 'asc' or 'desc'");
        }
        Sort sort= Sort.by(direction,sortBy);
        Pageable pageable= PageRequest.of(pageNumber,size,sort);
        Page<Teacher> page= teacherRepository.findAll(pageable);

        List<TeacherDto> dtoList=new ArrayList<>();
        for(Teacher t : page.getContent()){
            dtoList.add(getDtoFromTeacher(t));
        }
        return new PagedResponse<>(
                dtoList,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    @Override
    @Transactional
    public TeacherDto updateTeacherById(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with Id: "+ id));
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());

        Teacher savedTeacher= teacherRepository.save(teacher);
        return getDtoFromTeacher(savedTeacher);
    }

    @Override
    @Transactional
    public void deleteTeacherById(Long id) {
        if(teacherRepository.existsById(id))
            throw new ResourceNotFoundException("Teacher not found with Id: "+ id);
        teacherRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getCoursesByTeacherId(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with Id: "+ id));

        List<CourseDto> courseDtoList=new ArrayList<>();
        for(Course course : teacher.getCourses()){
            CourseMaterialDto materialDto = CourseMaterialDto.builder()
                    .courseMaterialId(course.getCourseMaterial().getCourseMaterialId())
                    .url(course.getCourseMaterial().getUrl())
                    .courseId(course.getCourseId())
                    .build();
            courseDtoList.add( CourseDto.builder()
                    .courseId(course.getCourseId())
                    .title(course.getTitle())
                    .credit(course.getCredit())
                    .teacherId(course.getTeacher().getTeacherId())
                    .courseMaterialDto(materialDto)
                    .build());
        }
        return courseDtoList;
    }

    private TeacherDto getDtoFromTeacher(Teacher teacher){
        return TeacherDto.builder()
                .teacherId(teacher.getTeacherId())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .email(teacher.getEmail())
                .build();
    }
}
