package com.lvargese.courseapi.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TeacherDto {
    private Long teacherId;
    @NotBlank(message = "First name is required")
    private String firstName;
    private String lastName;
    @Email(message = "Not a valid email format")
    @NotBlank
    private String email;
}
