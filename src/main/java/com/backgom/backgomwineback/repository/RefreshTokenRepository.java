package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.User.RefreshToken;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;
import java.util.UUID;

@Mapper
public interface RefreshTokenRepository {

    Optional<RefreshToken> findByUserId(UUID userId);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    Boolean existsByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    void save(RefreshToken refreshToken);
}
