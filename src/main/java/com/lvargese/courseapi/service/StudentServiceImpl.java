package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.dto.StudentDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Guardian;
import com.lvargese.courseapi.entity.Student;
import com.lvargese.courseapi.exception.InvalidRequestException;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.StudentRepository;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public StudentDto createStudent(StudentDto dto) {

        Guardian guardian = Guardian.builder()
                .name(dto.getGuardianName())
                .mobile(dto.getGuardianMobile())
                .email(dto.getGuardianEmail())
                .build();

        Student student=Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .emailId(dto.getEmailId())
                .guardian(guardian)
                .build();
        Student savedStudent= studentRepository.save(student);
        return getDtoFromStudent(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with Id: "+id ));
        return getDtoFromStudent(student);
    }

    @Override
    public PagedResponse<StudentDto> getAllStudents(Integer pageNumber, Integer size, String sortBy, String dir) {
        Sort.Direction direction;
        try{
            direction =Sort.Direction.fromString(dir);
        }
        catch (IllegalArgumentException e){
            throw new InvalidRequestException("Invalid sorting direction. Use 'asc' or 'desc'");
        }
        Sort sort= Sort.by(direction,sortBy);
        Pageable pageable= PageRequest.of(pageNumber,size,sort);
        Page<Student> page= studentRepository.findAll(pageable);

        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student s : page.getContent())
            studentDtoList.add(getDtoFromStudent(s));
        return new PagedResponse<>(
                studentDtoList,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize()
        );
    }

    @Override
    @Transactional
    public StudentDto updateStudentById(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with Id: "+id ));

        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmailId(dto.getEmailId());
        student.getGuardian().setName(dto.getGuardianName());
        student.getGuardian().setMobile(dto.getGuardianMobile());
        student.getGuardian().setEmail(dto.getGuardianEmail());

        Student savedStudent = studentRepository.save(student);

        return getDtoFromStudent(savedStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(Long id) {
        studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with Id: "+id ));
        studentRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getCoursesEnrolledByStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with Id: "+id ));
        Set<Course> courses= student.getCourses();
        List<CourseDto> courseDtoList = new ArrayList<>();
        if(courses == null)
            return courseDtoList;
        for(Course course : courses){
            courseDtoList.add(getCourseDtoFromCourse(course));
        }
        return courseDtoList;
    }

    private StudentDto getDtoFromStudent(Student student){

        List<CourseDto> courseDtoList = null;
        Set<Course> courses=  student.getCourses();
        if(courses!=null && !courses.isEmpty()) {
            courseDtoList = new ArrayList<>();
            for(Course c : courses){
                courseDtoList.add(getCourseDtoFromCourse(c));
            }
        }

        return StudentDto.builder()
                .studentId(student.getStudentId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailId(student.getEmailId())
                .guardianName(student.getGuardian().getName())
                .guardianMobile(student.getGuardian().getMobile())
                .guardianEmail(student.getGuardian().getEmail())
                .courses(courseDtoList)
                .build();
    }

    private CourseDto getCourseDtoFromCourse(Course course){
        CourseMaterialDto materialDto = null;

        if(course.getCourseMaterial() != null) {
            materialDto = CourseMaterialDto.builder()
                    .courseMaterialId(course.getCourseMaterial().getCourseMaterialId())
                    .url(course.getCourseMaterial().getUrl())
                    .courseId(course.getCourseId())
                    .build();
        }
        return CourseDto.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .credit(course.getCredit())
                .teacherId(course.getTeacher().getTeacherId())
                .courseMaterialDto(materialDto)
                .build();
    }
}
