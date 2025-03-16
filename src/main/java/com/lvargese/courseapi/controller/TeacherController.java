package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseDto;
import com.lvargese.courseapi.dto.TeacherDto;
import com.lvargese.courseapi.service.TeacherService;
import com.lvargese.courseapi.utils.AppConstants;
import com.lvargese.courseapi.utils.PagedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@Tag(name = "Teachers-Endpoints")
public class TeacherController {
    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Operation(summary = "Create Teacher")
    @PostMapping
    private ResponseEntity<TeacherDto> createTeacher(@RequestBody @Valid TeacherDto teacherDto){
        return new ResponseEntity<>(teacherService.createTeacher(teacherDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all Teachers")
    @GetMapping
    private ResponseEntity<PagedResponse<TeacherDto>> getAllTeachers(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) Integer page,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) Integer size,
            @RequestParam(defaultValue = "firstName",required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String dir) {

        return ResponseEntity.ok(teacherService.getAllTeachers(page, size, sortBy, dir));

    }

    @Operation(summary = "Get Teacher")
    @GetMapping("/{id}")
    private ResponseEntity<TeacherDto> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @Operation(summary = "Update Teacher")
    @PutMapping("/{id}")
    private ResponseEntity<TeacherDto> updateTeacherById(@PathVariable Long id, @RequestBody @Valid TeacherDto dto){
        return ResponseEntity.ok(teacherService.updateTeacherById(id,dto));
    }

    @Operation(summary = "Delete Teacher")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteTeacherById(@PathVariable Long id){
        teacherService.deleteTeacherById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get course of Teacher")
    @GetMapping("/{id}/courses")
    private ResponseEntity<List<CourseDto>> getCoursesByTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getCoursesByTeacherId(id));
    }
}
