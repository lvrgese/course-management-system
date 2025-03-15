package com.lvargese.courseapi.controller;

import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.service.CourseMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materials")
public class CourseMaterialController {
    private final CourseMaterialService courseMaterialService;

    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @PostMapping
    private ResponseEntity<CourseMaterialDto> createCourseMaterial(@RequestBody @Valid CourseMaterialDto dto){
        return new ResponseEntity<>(courseMaterialService.createCourseMaterial(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    private ResponseEntity<CourseMaterialDto> getCourseMaterialById(@PathVariable Long id){
        return ResponseEntity.ok(courseMaterialService.getCourseMaterialById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<CourseMaterialDto> updateCourseMaterialById(@PathVariable Long id,@RequestBody @Valid CourseMaterialDto dto){
        return ResponseEntity.ok(courseMaterialService.updateMaterialById(id,dto));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCourseMaterialById(@PathVariable Long id){
        courseMaterialService.deleteMaterialById(id);
        return ResponseEntity.noContent().build();
    }
 }
