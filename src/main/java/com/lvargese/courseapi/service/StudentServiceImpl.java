package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.dto.StudentDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Guardian;
import com.lvargese.courseapi.entity.Student;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtoList = new ArrayList<>();
        for(Student s : students)
            studentDtoList.add(getDtoFromStudent(s));
        return studentDtoList;
    }

    @Override
    @Transactional
    public StudentDto updateStudentById(Long id, StudentDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Student not found with Id: "+id ));
        Guardian guardian = Guardian.builder()
                .name(dto.getGuardianName())
                .mobile(dto.getGuardianMobile())
                .email(dto.getGuardianEmail())
                .build();

        Student updatedStudent=Student.builder()
                .studentId(student.getStudentId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .emailId(dto.getEmailId())
                .guardian(guardian)
                .build();
        Student savedStudent= studentRepository.save(updatedStudent);
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
        List<Course> courses= student.getCourses();
        List<CourseDto> courseDtoList = new ArrayList<>();

        for(Course course : courses){
            courseDtoList.add(getCourseDtoFromCourse(course));
        }
        return courseDtoList;
    }

    private StudentDto getDtoFromStudent(Student student){
        return StudentDto.builder()
                .studentId(student.getStudentId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailId(student.getEmailId())
                .guardianName(student.getGuardian().getName())
                .guardianMobile(student.getGuardian().getMobile())
                .guardianEmail(student.getGuardian().getEmail())
                .build();
    }

    private CourseDto getCourseDtoFromCourse(Course course){
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
