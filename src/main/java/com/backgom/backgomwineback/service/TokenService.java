package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.domain.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        UUID userId = refreshTokenService.findByRefreshToken(refreshToken)
                .getUserId();

        UserEntity user = userService.findById(userId);

        return tokenProvider.generateToken(user, Duration.ofHours(2));

    }
}
