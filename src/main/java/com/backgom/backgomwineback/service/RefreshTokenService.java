package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.domain.RefreshToken;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalStateException("Unexpected Token"));
    }

}
