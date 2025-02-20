package com.lvargese.courseapi.repository;

import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.CourseMaterial;
import com.lvargese.courseapi.entity.Guardian;
import com.lvargese.courseapi.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

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

    @Test
    @Rollback(true)
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

    @Test
    public void printAllStudents(){
        List<Student> studs= studentRepository.findAll();
        System.out.println("students = " + studs);
    }

    @Test
    public void printStudentById(){
        Student student = studentRepository.findByStudentId(100L);
        System.out.println("student with id 100 = " + student);
    }

    public void printStudentById(Long id){
        Student student = studentRepository.findByStudentId(id);
        System.out.println("student with id 100 = " + student);
    }

    @Test
    public void getStudentsByPattern(){
        List<Student> students= studentRepository.getStudentByPatternNative();
        System.out.println("students with S in guardian name = " + students);
    }

    @Test
    public void getStudentsByCustomName(){
        Student student= studentRepository.getStudentByCustomFirstName("Liyons");
        System.out.println("students with first name Liyons = " + student);
    }

    @Test
    public void getStudentsByCustomNameNamedParam(){
        Student student= studentRepository.getStudentByCustomFirstNameNamedParam("Liyons");
        System.out.println("students with first name Liyons as named param = " + student);
    }
    @Test
    @Rollback(true)
    public void updateStudentNameById(){
        studentRepository.updateStudentNameById(100L,"LiyonsV");
        printStudentById(100L);
    }
    @Test
    public void paginationTest(){

        Pageable pageable= PageRequest.of(0,1);

        Page<Student> page = studentRepository.findAll(pageable);

        List<Student> students = page.getContent();

        var count= page.getTotalElements();
        System.out.println("Student-1st page with 1 record = " + students + "No: of students " + count);


    }

    @Test
    public void sortRecords(){
        List<Student> students = studentRepository.findAll(Sort.by("firstName"));

        System.out.println("students sorted by first name = " + students);
    }
    
    @Test
    public void paginationWithSort() {
        Pageable firstPageWithTwoRecords= PageRequest.of(0,2,Sort.by("lastName"));
        
        List<Student> students = studentRepository.findAll(firstPageWithTwoRecords).getContent();
        System.out.println("students = " + students);
    }

    @Test
    public void findStudentsContainingUsingPagination(){
        Pageable page = PageRequest.of(0,3,Sort.by("firstName"));

        List<Student> students = studentRepository.findByFirstNameContaining("L",page).getContent();

        System.out.println("students with L in name  = " + students);
    }

    @Test
    @Rollback(true)
    public void saveStudentWithCourseAndGuardian(){
        Guardian guardian = Guardian.builder()
                .name("Mohan")
                .email("mohan@gmail.com")
                .mobile("9834784803")
                .build();

        CourseMaterial material = CourseMaterial.builder()
                .url("github.org")
                .build();
        Course course= Course.builder()
                .title("AI")
                .credit(5)
                .courseMaterial(material)
                .build();

        Student student = Student.builder()
                .firstName("Abhishek")
                .lastName("N")
                .emailId("abhishek@gmail.com")
                .guardian(guardian)
                .build();
        student.addCourse(course);

        studentRepository.save(student);
    }
  
}