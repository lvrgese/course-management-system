package com.lvargese.courseapi.repository;

import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Student> findByFirstNameContaining(String name, Pageable pageable);

    boolean existsByStudentIdAndCoursesContains(Long studentId, Course course);

    @Query(value = "SELECT s.*, sc.student_id AS sc_student_id " +
            "FROM tbl_student s " +
            "INNER JOIN student_course sc ON s.student_id = sc.student_id " +
            "WHERE sc.course_id = ?1",
            nativeQuery = true)
    List<Student> findStudentsByCourseId(Long courseId);

}
