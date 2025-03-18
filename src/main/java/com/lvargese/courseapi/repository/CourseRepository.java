package com.lvargese.courseapi.repository;

import com.lvargese.courseapi.entity.Course;
import com.lvargese.courseapi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    List<Course> findByTeacher(Teacher teacher);

    @Query(
            value = "SELECT * FROM course where course_material_id=?1",
            nativeQuery = true
    )
    Optional<Course> findByCourseMaterialId(Long courseMaterialId);
}
