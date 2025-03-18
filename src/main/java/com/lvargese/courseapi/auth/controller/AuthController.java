package com.lvargese.courseapi.auth.controller;
import com.lvargese.courseapi.auth.service.AuthService;
import com.lvargese.courseapi.auth.utils.AuthResponse;
import com.lvargese.courseapi.auth.utils.LoginRequest;
import com.lvargese.courseapi.auth.utils.RefreshTokenRequest;
import com.lvargese.courseapi.auth.utils.RegisterRequest;
import com.lvargese.courseapi.exception.InvalidRefreshTokenException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    private ResponseEntity<AuthResponse> registerUser(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    private ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/refresh-token")
    private ResponseEntity<AuthResponse> getAccessTokenFromRefreshToken(@RequestBody @Valid RefreshTokenRequest request) throws InvalidRefreshTokenException {
        return ResponseEntity.ok(authService.getNewAccessToken(request.getRefreshToken()));
    }
}
