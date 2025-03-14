package com.lvargese.courseapi.repository;

import com.lvargese.courseapi.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

    List<Grade> findAllByStudent_StudentId(Long studentId);
    
    List<Grade> findAllByCourse_CourseId(Long courseId);

    Optional<Grade> findByStudent_StudentIdAndCourse_CourseId(Long studentId, Long courseId);
}
