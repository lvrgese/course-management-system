package com.lvargese.courseapi.auth.service;

import com.lvargese.courseapi.auth.entity.RefreshToken;
import com.lvargese.courseapi.auth.entity.User;
import com.lvargese.courseapi.auth.entity.UserRole;
import com.lvargese.courseapi.auth.reposiory.UserRepository;
import com.lvargese.courseapi.auth.utils.AuthResponse;
import com.lvargese.courseapi.auth.utils.LoginRequest;
import com.lvargese.courseapi.auth.utils.RegisterRequest;
import com.lvargese.courseapi.exception.InvalidRefreshTokenException;
import com.lvargese.courseapi.exception.InvalidRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerUser(RegisterRequest registerRequest){
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new InvalidRequestException("User already exists");
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(UserRole.USER)
                .username(registerRequest.getUsername())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
        User savedUser = userRepository.save(user);

        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken= refreshTokenService.createRefreshToken(savedUser.getUsername());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassWord())
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                ()-> new UsernameNotFoundException("User name not found")
        );
        var accessToken = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }

    public AuthResponse getNewAccessToken(String refreshToken) throws InvalidRefreshTokenException {
        RefreshToken token = refreshTokenService.validateRefreshToken(refreshToken);
        User user = token.getUser();

        var accessToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
