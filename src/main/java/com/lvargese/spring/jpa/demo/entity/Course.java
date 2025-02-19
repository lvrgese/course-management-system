package com.lvargese.spring.jpa.demo.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String title;
    private Integer credit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_material_id")
    private CourseMaterial courseMaterial;
}
