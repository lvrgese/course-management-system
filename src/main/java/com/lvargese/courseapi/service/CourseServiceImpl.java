package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.dto.StudentDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.entity.Student;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.DuplicateEnrollmentException;
import com.lvargese.courseapi.exception.InvalidRequestException;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.CourseRepository;
import com.lvargese.courseapi.repository.StudentRepository;
import com.lvargese.courseapi.repository.TeacherRepository;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Teacher teacher=null;
        if(courseDto.getTeacherId() != null && courseDto.getTeacherId() > 0)
        {
            teacher = teacherRepository.findById(courseDto.getTeacherId())
                    .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with id : "+ courseDto.getTeacherId()));
        }

        Course course = Course.builder()
                .title(courseDto.getTitle())
                .credit(courseDto.getCredit())
                .teacher(teacher)
                .students(new HashSet<>())
                .build();
        Course savedCourse =  courseRepository.save(course);

        return getDtoFromCourse(savedCourse);
    }

    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course is not found with id : "+ id));
        return  getDtoFromCourse(course);
    }

    @Override
    public PagedResponse<CourseDto> getAllCourses(Integer pageNumber, Integer size, String sortBy, String dir) {
        Sort.Direction direction;
        try{
            direction =Sort.Direction.fromString(dir);
        }
        catch (IllegalArgumentException e){
            throw new InvalidRequestException("Invalid sorting direction. Use 'asc' or 'desc'");
        }
        Sort sort= Sort.by(direction,sortBy);
        Pageable pageable= PageRequest.of(pageNumber,size,sort);
        Page<Course> page= courseRepository.findAll(pageable);
        List<CourseDto> dtoList=new ArrayList<>();
        for(Course c : page.getContent()){
            dtoList.add(getDtoFromCourse(c));
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
    public CourseDto updateCourseById(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course is not found with id : "+ id));

        //Validation is already done at controller level
        course.setTitle(courseDto.getTitle());
        course.setCredit(courseDto.getCredit());
        if(courseDto.getTeacherId() != null && courseDto.getTeacherId() > 0)
        {
            Teacher teacher = teacherRepository.findById(courseDto.getTeacherId())
                    .orElseThrow(()-> new ResourceNotFoundException("Teacher not found with id : "+ courseDto.getTeacherId()));
            course.setTeacher(teacher);
        }
        if(courseDto.getCourseMaterialDto() != null){
            CourseMaterial material = course.getCourseMaterial();
            material.setUrl(courseDto.getCourseMaterialDto().getUrl());
            course.setCourseMaterial(material);
        }
        Course savedCourse = courseRepository.save(course);
        return getDtoFromCourse(savedCourse);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long id) {
        if(! courseRepository.existsById(id))
            throw new ResourceNotFoundException("Course is not found with id : "+ id);
        courseRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> getStudentsByCourseId(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course is not found with id : "+ id));
        if(course.getStudents().isEmpty())
            return List.of();
        Set<Student> students = course.getStudents();
        List<StudentDto> studentDtoList =new ArrayList<>();

        for(Student s : students){
            studentDtoList.add(
                    StudentDto.builder()
                            .studentId(s.getStudentId())
                            .firstName(s.getFirstName())
                            .lastName(s.getLastName())
                            .emailId(s.getEmailId())
                            .guardianName(s.getGuardian().getName())
                            .guardianMobile(s.getGuardian().getMobile())
                            .guardianEmail(s.getGuardian().getEmail())
                            .build()
            );
        }
        return studentDtoList;
    }

    @Override
    @Transactional
    public CourseDto enrollStudentInCourse(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new ResourceNotFoundException("Course is not found with id : "+ courseId));
        Student student = studentRepository.findById(studentId).orElseThrow(
                ()-> new ResourceNotFoundException("Student not found with Id: "+studentId)
        );
        if(studentRepository.existsByStudentIdAndCoursesContains(studentId, course))
            throw new DuplicateEnrollmentException("This student is already enrolled in this list");
        course.addStudent(student);
        student.addCourse(course);
        Course savedCourse = courseRepository.save(course);
        return getDtoFromCourse(savedCourse);
    }

    private CourseDto getDtoFromCourse(Course course){
        CourseMaterialDto materialDto = CourseMaterialDto.builder()
                .courseMaterialId(course.getCourseMaterial().getCourseMaterialId())
                .url(course.getCourseMaterial().getUrl())
                .courseId(course.getCourseId())
                .build();
        return CourseDto.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .credit(course.getCredit())
                .teacherId(course.getTeacher().getTeacherId())
                .courseMaterialDto(materialDto)
                .build();
    }
}
