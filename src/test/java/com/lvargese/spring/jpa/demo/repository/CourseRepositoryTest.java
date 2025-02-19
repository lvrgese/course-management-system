package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Course;
import com.lvargese.spring.jpa.demo.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    //@Test
    public void saveCourse(){
        CourseMaterial material = CourseMaterial.builder()
                .url("www.google.com")
                .build();
        Course course= Course.builder()
                .title("JPA Course")
                .credit(5)
                .courseMaterial(material)
                .build();
        courseRepository.save(course);
    }
    
    @Test
    public void getCourses(){
        List<Course> courses = courseRepository.findAll();
        System.out.println("courses = " + courses.get(0).getTitle());
        
    }
}