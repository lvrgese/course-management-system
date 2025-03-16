package com.lvargese.courseapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(length = 50)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String emailId;
    @Embedded
    private Guardian guardian;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(
            name="student_course",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "studentId"),
            inverseJoinColumns = @JoinColumn(name = "course_id",referencedColumnName = "courseId")
    )
    private Set<Course> courses;
    public void addCourse(Course course){
        if(courses == null)
            courses= new HashSet<>();
        courses.add(course);
    }
}
