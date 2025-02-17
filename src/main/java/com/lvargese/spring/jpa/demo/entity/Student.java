package com.lvargese.spring.jpa.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="tbl_student", uniqueConstraints = @UniqueConstraint(
        columnNames = {"emailId","guardianEmail"}
))
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long studentId;

    @Column(nullable = false,length = 50)
    private String firstName;

    private String lastName;
    private String emailId;

    @Column(nullable = false)
    private String guardianName;

    private String guardianEmail;
    private String guardianMobile;
}
