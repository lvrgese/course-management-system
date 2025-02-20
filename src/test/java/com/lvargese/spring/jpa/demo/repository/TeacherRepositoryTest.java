package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Course;
import com.lvargese.spring.jpa.demo.entity.Teacher;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;


@SpringBootTest
class TeacherRepositoryTest {
    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    @Rollback(true)
    @Transactional
    public void saveTeacher(){
        Course course = Course.builder()
                .title("DSA")
                .credit(5)
                .build();
        Teacher teacher= Teacher.builder()
                .firstName("Suresh")
                .lastName("PT")
                .courses(List.of(course))
                .build();
        teacherRepository.save(teacher);
    }
    

}