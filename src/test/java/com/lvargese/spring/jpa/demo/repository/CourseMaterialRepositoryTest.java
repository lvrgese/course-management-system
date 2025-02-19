package com.lvargese.spring.jpa.demo.repository;

import com.lvargese.spring.jpa.demo.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {
    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    public void getCourseMaterials(){
        List<CourseMaterial> materials = courseMaterialRepository.findAll();
        System.out.println("course materials = " + materials);
    }
}