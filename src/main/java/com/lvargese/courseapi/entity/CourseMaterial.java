package com.lvargese.courseapi.entity;

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
public class CourseMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseMaterialId;
    @Column(nullable = false,unique = true)
    private String url;

    @OneToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;
}
