package com.lvargese.courseapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private Long studentId;
    @NotBlank(message = "Student first name is required")
    private String firstName;
    private String lastName;
    @Email(message = "Student email format is invalid ")
    @NotBlank(message = "Student email is required")
    private String emailId;
    @NotBlank(message = "Guardian name is required")
    private String guardianName;
    @Email(message = "Guardian email format is invalid ")
    @NotBlank(message = "Student email is required")
    private String guardianEmail;
    @NotBlank(message = "Guardian mobile number is required")
    private String guardianMobile;
    private List<CourseDto> courses;
}
