package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.StudentDto;
import com.lvargese.courseapi.service.CourseService;
import com.lvargese.courseapi.utils.AppConstants;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    private ResponseEntity<PagedResponse<CourseDto>> getAllCourses(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size,
            @RequestParam(defaultValue = "title",required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String dir){
        return ResponseEntity.ok(courseService.getAllCourses(page,size,sortBy,dir));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private CourseDto createCourse(@RequestBody @Valid CourseDto dto){
        return  courseService.createCourse(dto);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CourseDto> getCourseById(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    private CourseDto updateCourseById(@PathVariable Long id, @RequestBody @Valid CourseDto dto){
        return  courseService.updateCourseById(id,dto);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCourseById(@PathVariable Long id){
        courseService.deleteCourseById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/students/{studentId}")
    private ResponseEntity<CourseDto> enrollStudentInACourse(@PathVariable Long courseId, @PathVariable Long studentId){
        return ResponseEntity.ok(courseService.enrollStudentInCourse(courseId,studentId));
    }

    @GetMapping("/{id}/students")
    private ResponseEntity<List<StudentDto>> getStudentsInACourse(@PathVariable Long id){
        return ResponseEntity.ok(courseService.getStudentsByCourseId(id));
    }

}
