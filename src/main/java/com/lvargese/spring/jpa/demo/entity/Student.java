package com.lvargese.spring.jpa.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="tbl_student", uniqueConstraints = @UniqueConstraint(
        columnNames = {"emailId","guardianEmail"}
))
@Builder
public class Student {

    @Id
    @SequenceGenerator(
            name = "id_sequence",
            initialValue = 100,
            allocationSize = 2
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_sequence")
    private Long studentId;

    @Column(nullable = false,length = 50)
    private String firstName;

    private String lastName;
    private String emailId;
    @Embedded
    private Guardian guardian;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name="student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "courseId")
    )
    private List<Course> courses;

    public void addCourse(Course course){
        if(courses == null)
            courses= new ArrayList<>();
        courses.add(course);
    }
}
