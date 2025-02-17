package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    public StudentRepository studentRepository;
    @Test
    public void saveStudent(){
        Student s = Student.builder()
                .emailId("liyons@gmail.com")
                .firstName("Liyons")
                .lastName("Varghese")
                .guardianName("Varghese")
                .guardianEmail("varghese@gmail.com")
                .guardianMobile("9447238790")
                .build();
        studentRepository.save(s);
    }
  
}