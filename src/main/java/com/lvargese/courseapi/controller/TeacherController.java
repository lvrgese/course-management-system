package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.entity.Teacher;
import com.lvargese.courseapi.service.TeacherService;
import com.lvargese.courseapi.utils.AppConstants;
import com.lvargese.courseapi.utils.PagedResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    private ResponseEntity<TeacherDto> createTeacher(@RequestBody @Valid TeacherDto teacherDto){
        return new ResponseEntity<>(teacherService.createTeacher(teacherDto), HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<PagedResponse<TeacherDto>> getAllTeachers(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size,
            @RequestParam(defaultValue = "firstName",required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String dir) {

        return ResponseEntity.ok(teacherService.getAllTeachers(page, size, sortBy, dir));

    }

    @GetMapping("/{id}")
    private ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<TeacherDto> updateTeacherById(@PathVariable Long id, @RequestBody @Valid TeacherDto dto){
        return ResponseEntity.ok(teacherService.updateTeacherById(id,dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTeacherById(@PathVariable Long id){
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/courses")
    private ResponseEntity<List<CourseDto>> getCoursesByTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getCoursesByTeacherId(id));
    }
}
