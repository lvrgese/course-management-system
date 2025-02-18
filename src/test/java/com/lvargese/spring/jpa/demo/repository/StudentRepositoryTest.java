package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Guardian;
import com.lvargese.spring.jpa.demo.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    public StudentRepository studentRepository;


//  @Test -- Without embedded guardian
//    public void saveStudent(){
//        Student s = Student.builder()
//                .emailId("liyons@gmail.com")
//                .firstName("Liyons")
//                .lastName("Varghese")
//                .guardianName("Varghese")
//                .guardianEmail("varghese@gmail.com")
//                .guardianMobile("9447238790")
//                .build();
//        studentRepository.save(s);
//    }

    //@Test
    public void saveStudentWithGuardian(){
        Guardian guardian = Guardian.builder()
                .name("Sura")
                .email("sureshannan123@gmail.com")
                .mobile("76787678656")
                .build();
        Student student = Student.builder()
                .firstName("Abhinav")
                .lastName("P V")
                .emailId("sufinmandi@gmail.com")
                .guardian(guardian)
                .build();
        studentRepository.save(student);
    }

    //@Test
    public void printAllStudents(){
        List<Student> studs= studentRepository.findAll();
        System.out.println("students = " + studs);
    }

    @Test
    public void printStudentById(){
        Student student = studentRepository.findByStudentId(100L);
        System.out.println("student with id 100 = " + student);
    }

    @Test
    public void getStudentsByPattern(){
        List<Student> students= studentRepository.getStudentByPatternNative();
        System.out.println("students with S in guardian name = " + students);
    }
  
}