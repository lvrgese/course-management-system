package com.lvargese.courseapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
