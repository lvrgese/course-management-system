package com.lvargese.spring.jpa.demo.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    fetch = FetchType.EAGER,
    optional = false)
    @JoinColumn(name = "course_material_id")
    private CourseMaterial courseMaterial;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;
}
