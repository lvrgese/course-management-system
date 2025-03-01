package com.lvargese.courseapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradeId;
    @Column(nullable = false)
    @Pattern(regexp = "[A-F]") //Only allows A,B,C,D,E,F
    private String gradeValue;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @Column(unique = true)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @Column(unique = true)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher assignedBy;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
