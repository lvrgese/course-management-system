package com.lvargese.courseapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;
    @Column(nullable = false, length = 50)
    private String firstName;
    @Column(length = 50)
    private String lastName;
    @Column(unique = true,nullable = false)
    private String email;
    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Course> courses;

    public void addCourse(Course course){
        if(courses == null)
            courses= new HashSet<>();
        courses.add(course);
    }
}
