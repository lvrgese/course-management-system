package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    Student findByStudentId(Long studentId);

    @Query(
            value = "select * from tbl_student where guardian_name LIKE 'S%'",
            nativeQuery = true
    )
    List<Student> getStudentByPatternNative();

    @Query(
            value = "select * from tbl_student where first_name=?1",
            nativeQuery = true
    )
    Student getStudentByCustomFirstName(String firstName);

    //Named param
    @Query(
            value = "select * from tbl_student where first_name=:first_name",
            nativeQuery = true
    )
    Student getStudentByCustomFirstNameNamedParam(@Param(value = "first_name") String firstName);

    @Modifying
    @Transactional
    @Query(
            value = "update tbl_student set first_name = ?2 where student_id=?1",
            nativeQuery = true
    )
    void updateStudentNameById(Long id, String name);
}
