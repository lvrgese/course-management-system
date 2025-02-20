package com.lvargese.courseapi.repository;

import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    //@Test
    //@Rollback(true)
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

    @Test
    @Rollback(true)
    public void saveCourseWithTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("Ravi")
                .lastName("K")
                .build();
        CourseMaterial material = CourseMaterial.builder()
                .url("www.google.com")
                .build();
        Course course= Course.builder()
                .title("JPA Course")
                .credit(5)
                .courseMaterial(material)
                .teacher(teacher)
                .build();
        courseRepository.save(course);
    }
}