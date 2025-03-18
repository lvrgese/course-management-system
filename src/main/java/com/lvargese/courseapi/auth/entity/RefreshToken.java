package com.lvargese.courseapi.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(nullable = false,length = 500)
    private String refreshToken;
    @NotNull
    private Instant expirationTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user")
    private User user;
}
