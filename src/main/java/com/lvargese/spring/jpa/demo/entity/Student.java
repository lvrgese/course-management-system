package com.lvargese.spring.jpa.demo.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private String guardianName;

    private String guardianEmail;
    private String guardianMobile;
}
