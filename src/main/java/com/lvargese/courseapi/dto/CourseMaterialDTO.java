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
public class CourseMaterialDTO {
    private Long courseMaterialId;
    @NotBlank(message = "URL can't be empty")
    private String url;
    @NotNull
    private Long courseId;
}
