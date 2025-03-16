package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.service.CourseMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materials")
@Tag(name = "Course-Materials-Endpoints")
public class CourseMaterialController {
    private final CourseMaterialService courseMaterialService;


    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @Operation(summary = "Create course material")
    @ApiResponse(responseCode = "201")
    @PostMapping
    private ResponseEntity<CourseMaterialDto> createCourseMaterial(@RequestBody @Valid CourseMaterialDto dto){
        return new ResponseEntity<>(courseMaterialService.createCourseMaterial(dto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get course material")
    @GetMapping("/{id}")
    private ResponseEntity<CourseMaterialDto> getCourseMaterialById(@PathVariable Long id){
        return ResponseEntity.ok(courseMaterialService.getCourseMaterialById(id));
    }

    @Operation(summary = "Update course material")
    @PutMapping("/{id}")
    private ResponseEntity<CourseMaterialDto> updateCourseMaterialById(@PathVariable Long id,@RequestBody @Valid CourseMaterialDto dto){
        return ResponseEntity.ok(courseMaterialService.updateMaterialById(id,dto));
    }

    @Operation(summary = "Delete course material")
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCourseMaterialById(@PathVariable Long id){
        courseMaterialService.deleteMaterialById(id);
        return ResponseEntity.noContent().build();
    }
 }
