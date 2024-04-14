package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByUserId(UUID userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Boolean existsByUserId(UUID userId);

    void deleteByUserId(UUID userId);
}
