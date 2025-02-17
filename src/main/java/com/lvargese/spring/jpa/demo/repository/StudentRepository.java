package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
}
