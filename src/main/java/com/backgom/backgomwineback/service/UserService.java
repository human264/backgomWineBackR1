package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.domain.LogInUser;
import com.backgom.backgomwineback.domain.RefreshToken;
import com.backgom.backgomwineback.domain.UserEntity;
import com.backgom.backgomwineback.dto.TokenDto;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import com.backgom.backgomwineback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;

    public UserEntity create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid Arguments");
        }
        final String email = userEntity.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected User"));
    }

    public TokenDto createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        UUID userId = refreshTokenService.findByRefreshToken(refreshToken)
                .getUserId();

        Optional<UserEntity> byId = userRepository.findById(userId);
        if (byId.isEmpty()) {
            throw new RuntimeException("User를 찾을 수 없습니다.");
        }
        UserEntity user = byId.get();

        return tokenProvider.generateToken(user, Duration.ofHours(2), Duration.ofDays(1), "user");

    }




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        LogInUser logInUser = new LogInUser();
        logInUser.setLogInInfo(userEntity);
        return logInUser;
    }

}
