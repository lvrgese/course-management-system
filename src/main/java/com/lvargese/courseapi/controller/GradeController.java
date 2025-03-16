package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.GradeDto;
import com.lvargese.courseapi.service.GradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@Tag(name = "Grades-Endpoints")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }
    @Operation(summary = "Create Grade")
    @PostMapping("/students/{studentId}/courses/{courseId}")
    private ResponseEntity<GradeDto> createGrade(@PathVariable Long studentId, @PathVariable Long courseId,
                                                 @RequestBody @Valid GradeDto gradeDto){
        return new ResponseEntity<>(gradeService.assignGrade(gradeDto,studentId,courseId), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Grade")
    @GetMapping("/{id}")
    private ResponseEntity<GradeDto> getGradeById(@PathVariable Long id){
        return ResponseEntity.ok(gradeService.getGradeById(id));
    }

    @Operation(summary = "Delete Grade")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteGradeById(@PathVariable Long id){
        gradeService.deleteGradeById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Grade")
    @PutMapping("/{id}")
    private ResponseEntity<GradeDto> updateGradeById(@PathVariable Long id, @RequestBody @Valid GradeDto dto){
        return ResponseEntity.ok(gradeService.updateGradeById(id,dto));
    }

    @Operation(summary = "Get Grades By Course Id and Student Id")
    @GetMapping("/students/{studentId}/courses/{courseId}")
    private ResponseEntity<GradeDto> getGradesByStudentIdAndCourseId(@PathVariable Long studentId, @PathVariable Long courseId){
        return ResponseEntity.ok(gradeService.getGradeByStudentAndCourse(studentId,courseId));
    }

    @Operation(summary = "Get All grades of student")
    @GetMapping("/students/{studentId}")
    private ResponseEntity<List<GradeDto>> getAllGradesByStudentId(@PathVariable Long studentId){
        return ResponseEntity.ok(gradeService.getAllGradesByStudentId(studentId));
    }

    @Operation(summary = "Get grades for a course")
    @GetMapping("/courses/{courseId}")
    private ResponseEntity<List<GradeDto>> getAllGradesByCourseId(@PathVariable Long courseId){
        return ResponseEntity.ok(gradeService.getGradesByCourseId(courseId));
    }
}
