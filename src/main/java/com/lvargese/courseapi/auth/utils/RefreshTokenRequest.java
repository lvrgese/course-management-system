package com.lvargese.courseapi.auth.utils;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Token can't be blank")
    private String refreshToken;
}
