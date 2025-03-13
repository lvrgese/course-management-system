package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.repository.CourseMaterialRepository;
import com.lvargese.courseapi.repository.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CourseMaterialServiceImpl implements CourseMaterialService{

    private final CourseRepository courseRepository;
    private final CourseMaterialRepository courseMaterialRepository;

    public CourseMaterialServiceImpl(CourseRepository courseRepository, CourseMaterialRepository courseMaterialRepository) {
        this.courseRepository = courseRepository;
        this.courseMaterialRepository = courseMaterialRepository;
    }


    @Override
    public CourseMaterialDto createCourseMaterial(CourseMaterialDto dto) {
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(()-> new RuntimeException("Course not found with Id : "+dto.getCourseId()));  //Todo: Custom exception

        CourseMaterial material = CourseMaterial.builder()
                .course(course)
                .url(dto.getUrl())
                .build();
        CourseMaterial savedMaterial = courseMaterialRepository.save(material);
        return  getDtoFromMaterial(savedMaterial);

    }

    @Override
    public CourseMaterialDto getCourseMaterialById(Long id) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Material not found with Id : "+id));
        return getDtoFromMaterial(material);
    }

    @Override
    public CourseMaterialDto updateMaterialById(Long id,CourseMaterialDto dto) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Material not found with Id : "+id));
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(()-> new RuntimeException("Course not found with Id : "+dto.getCourseId()));  //Todo: Custom exception
        material.setUrl(dto.getUrl());
        material.setCourse(course);
        CourseMaterial savedMaterial = courseMaterialRepository.save(material);
        return  getDtoFromMaterial(savedMaterial);
    }

    @Override
    public String deleteMaterialById(Long id) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Material not found with Id : "+id));
        courseMaterialRepository.deleteById(id);
        return "Successfully Deleted";
    }

    private CourseMaterialDto getDtoFromMaterial(CourseMaterial material){
        return  CourseMaterialDto.builder()
                .courseMaterialId(material.getCourseMaterialId())
                .url(material.getUrl())
                .courseId(material.getCourse().getCourseId())
                .build();
    }
}
