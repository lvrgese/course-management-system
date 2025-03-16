package com.lvargese.courseapi.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"courseMaterial","teacher"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    @Column(unique = true,nullable = false)
    private String title;
    @Min(1)
    @Max(10)
    @Column(nullable = false)
    private Integer credit;
    @OneToOne(cascade = CascadeType.ALL,
    optional = false)
    @JoinColumn(name = "course_material_id")
    private CourseMaterial courseMaterial;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;


    @ManyToMany(mappedBy = "courses",
            fetch = FetchType.LAZY)
    private List<Student> students;

    public void addStudent(Student student){
        if(students == null)
            students= new ArrayList<>();
        students.add(student);
    }
}
