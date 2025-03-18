package com.lvargese.courseapi.auth.service;

import com.lvargese.courseapi.auth.entity.RefreshToken;
import com.lvargese.courseapi.auth.entity.User;
import com.lvargese.courseapi.auth.reposiory.RefreshTokenRepository;
import com.lvargese.courseapi.auth.reposiory.UserRepository;
import com.lvargese.courseapi.exception.InvalidRefreshTokenException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));

        RefreshToken token = user.getRefreshToken();
        if(token != null)
            return token;
        long expirationTime = 7*24*60*60*1000;
        RefreshToken generatedToken = RefreshToken.builder()
                .user(user)
                .refreshToken(UUID.randomUUID().toString())
                .expirationTime(Instant.now().plusMillis(expirationTime))
                .build();
        return refreshTokenRepository.save(generatedToken);
    }

    public RefreshToken validateRefreshToken(String token) throws InvalidRefreshTokenException {
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new InvalidRefreshTokenException("Refresh token is not valid"));
        if (refreshToken.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidRefreshTokenException("Refresh Token Expired");
        }
        return refreshToken;
    }
}
