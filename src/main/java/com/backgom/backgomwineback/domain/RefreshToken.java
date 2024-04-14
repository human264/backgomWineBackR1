package com.backgom.backgomwineback.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "user_id", nullable = false, updatable = true)
    private UUID userId;


    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(UUID userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}
