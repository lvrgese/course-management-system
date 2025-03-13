package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.GradeDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Grade;
import com.lvargese.courseapi.entity.Student;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.CourseRepository;
import com.lvargese.courseapi.repository.GradeRepository;
import com.lvargese.courseapi.repository.StudentRepository;
import com.lvargese.courseapi.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public GradeServiceImpl(GradeRepository gradeRepository, StudentRepository studentRepository,
                            TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public GradeDto assignGrade(GradeDto dto) {

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(()->new ResourceNotFoundException("Student not found with Id: "+dto.getStudentId()));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with Id: "+dto.getTeacherId()));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(()->new ResourceNotFoundException("Course not found with Id: "+dto.getCourseId()));

        Grade grade= Grade.builder()
                .gradeValue(dto.getGradeValue())
                .assignedBy(teacher)
                .student(student)
                .course(course)
                .build();
        Grade savedGrade = gradeRepository.save(grade);
        return getDtoFromGrade(savedGrade);
    }

    @Override
    public GradeDto getGradeById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with Id: "+id));
        return getDtoFromGrade(grade);
    }

    @Override
    public GradeDto getGradeByStudentAndCourse(Long studentId, Long courseId) {
        Grade grade = gradeRepository.findByStudent_StudentIdAndCourse_CourseId(studentId,courseId)
                .orElseThrow(()-> new ResourceNotFoundException("Grade is not found for Student Id : " + studentId + " ,Course Id: "+ courseId));
        return getDtoFromGrade(grade);
    }

    @Override
    public List<GradeDto> getAllGrades() {
        List<Grade> gradeList = gradeRepository.findAll();
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for(Grade grade : gradeList){
            gradeDtoList.add(getDtoFromGrade(grade));
        }
        return gradeDtoList;
    }

    @Override
    public List<GradeDto> getAllGradesByStudentId(Long studentId) {
        List<Grade> gradeList = gradeRepository.findAllByStudent_StudentId(studentId);
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for(Grade grade : gradeList){
            gradeDtoList.add(getDtoFromGrade(grade));
        }
        return gradeDtoList;
    }

    @Override
    public List<GradeDto> getGradesByCourseId(Long courseId) {
        List<Grade> gradeList = gradeRepository.findAllByCourse_CourseId(courseId);
        List<GradeDto> gradeDtoList = new ArrayList<>();
        for(Grade grade : gradeList){
            gradeDtoList.add(getDtoFromGrade(grade));
        }
        return gradeDtoList;
    }

    @Override
    @Transactional
    public GradeDto updateGradeById(Long id, GradeDto dto) {
        gradeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with Id: "+id));

        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(()->new ResourceNotFoundException("Student not found with Id: "+dto.getStudentId()));
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with Id: "+dto.getTeacherId()));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(()->new ResourceNotFoundException("Course not found with Id: "+dto.getCourseId()));

        Grade grade= Grade.builder()
                .gradeId(dto.getGradeId())
                .gradeValue(dto.getGradeValue())
                .assignedBy(teacher)
                .student(student)
                .course(course)
                .build();
        Grade savedGrade = gradeRepository.save(grade);
        return getDtoFromGrade(savedGrade);
    }

    @Override
    @Transactional
    public void deleteGradeById(Long id) {
        if(! gradeRepository.existsById(id))
            throw  new ResourceNotFoundException("Grade not found with Id: "+id);
        gradeRepository.deleteById(id);
    }

    private GradeDto getDtoFromGrade(Grade grade){
        return GradeDto.builder()
                .gradeId(grade.getGradeId())
                .gradeValue(grade.getGradeValue())
                .teacherId(grade.getAssignedBy().getTeacherId())
                .studentId(grade.getStudent().getStudentId())
                .courseId(grade.getCourse().getCourseId())
                .createdAt(grade.getCreatedAt())
                .updatedAt(grade.getUpdatedAt())
                .build();
    }
}
