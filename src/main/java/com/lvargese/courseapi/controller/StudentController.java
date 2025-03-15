package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.StudentDto;
import com.lvargese.courseapi.service.StudentService;
import com.lvargese.courseapi.utils.AppConstants;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    private ResponseEntity<StudentDto> createStudent(@RequestBody @Valid StudentDto dto){
        return new ResponseEntity<>(studentService.createStudent(dto), HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<PagedResponse<StudentDto>> getAllStudents(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size,
            @RequestParam(defaultValue = "firstName",required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String dir
    ){
        return ResponseEntity.ok(studentService.getAllStudents(page, size, sortBy, dir));
    }

    @GetMapping("/{id}")
    private ResponseEntity<StudentDto> getStudentById(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<StudentDto> updateStudentById(@PathVariable Long id, @RequestBody @Valid StudentDto studentDto){
        return ResponseEntity.ok(studentService.updateStudentById(id,studentDto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/courses")
    private ResponseEntity<List<CourseDto>> getCoursesByStudentId(@PathVariable Long id){
        return ResponseEntity.ok(studentService.getCoursesEnrolledByStudentById(id));
    }
}
