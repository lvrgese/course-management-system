package com.lvargese.courseapi.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeDto {
    private Long gradeId;
    @NotBlank(message = "Grade is required")
    @Pattern(regexp = "[A-F]")
    private String gradeValue;
    private Long studentId;
    private Long courseId;
    @NotNull
    private Long teacherId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
