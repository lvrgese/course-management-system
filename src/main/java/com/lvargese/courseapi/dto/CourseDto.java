package com.lvargese.courseapi.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDto {
    private Long courseId;
    @NotBlank(message = "Title is required")
    private String title;
    @NotNull(message = "Credit is required")
    @Min(1)
    @Max(10)
    private Integer credit;
    @NotNull(message = "Teacher id is required")
    private Long teacherId;
    @NotNull(message = "Material id is required")
    private Long courseMaterialId;
}
