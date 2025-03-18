package com.lvargese.courseapi.service;

import com.lvargese.courseapi.dto.CourseMaterialDto;
import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.exception.ResourceNotFoundException;
import com.lvargese.courseapi.repository.CourseMaterialRepository;
import com.lvargese.courseapi.repository.CourseRepository;
import jakarta.transaction.Transactional;
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
                .orElseThrow(()-> new ResourceNotFoundException("Course not found with Id : "+dto.getCourseId()));

        CourseMaterial material = CourseMaterial.builder()
                .url(dto.getUrl())
                .build();
        course.setCourseMaterial(material);
        Course savedCourse = courseRepository.save(course);
        return  getDtoFromMaterial(savedCourse.getCourseMaterial());
    }

    @Override
    public CourseMaterialDto getCourseMaterialById(Long id) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course material not found with Id : "+id));
        return getDtoFromMaterial(material);
    }

    @Override
    @Transactional
    public CourseMaterialDto updateMaterialById(Long id,CourseMaterialDto dto) {
        CourseMaterial material = courseMaterialRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course material not found with Id : "+id));
        material.setUrl(dto.getUrl());
        CourseMaterial savedMaterial = courseMaterialRepository.save(material);
        return  getDtoFromMaterial(savedMaterial);
    }

    @Override
    @Transactional
    public void deleteMaterialById(Long id) {
        CourseMaterial material = courseMaterialRepository.findById(id).orElseThrow(()->
           new ResourceNotFoundException("Course material not found with Id : " + id));
        Course course = courseRepository.findByCourseMaterialId(id)
                .orElseThrow(()-> new ResourceNotFoundException("Course not found for course material"));
        course.setCourseMaterial(null);
        courseRepository.save(course);
        courseMaterialRepository.delete(material);
    }

    private CourseMaterialDto getDtoFromMaterial(CourseMaterial material){
        Course course =courseRepository.findByCourseMaterialId(material.getCourseMaterialId())
                .orElseThrow(()-> new ResourceNotFoundException("Course not found for course material"));

        return  CourseMaterialDto.builder()
                .courseMaterialId(material.getCourseMaterialId())
                .url(material.getUrl())
                .courseId(course.getCourseId())
                .build();
    }
}
