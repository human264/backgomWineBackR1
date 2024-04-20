package com.backgom.backgomwineback.domain.User;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Alias("RefreshToken")
public class RefreshToken {

    private Long id;

    private UUID userId;

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
