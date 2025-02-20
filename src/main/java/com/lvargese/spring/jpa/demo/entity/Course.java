package com.lvargese.spring.jpa.demo.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = "courseMaterial")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String title;
    private Integer credit;

    @OneToOne(cascade = CascadeType.ALL,
    fetch = FetchType.LAZY,
    optional = false)
    @JoinColumn(name = "course_material_id")
    private CourseMaterial courseMaterial;
}
