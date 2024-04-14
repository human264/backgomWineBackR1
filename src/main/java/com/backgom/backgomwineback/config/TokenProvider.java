package com.backgom.backgomwineback.config;


import com.backgom.backgomwineback.dto.TokenDto;
import com.backgom.backgomwineback.properties.JwtProperties;
import com.backgom.backgomwineback.domain.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public TokenDto generateToken(UserEntity user, Duration expiredAt, Duration expiredAtRefreshToken, String joinedUser) {
        Date now = new Date();
        return TokenDto.builder()
                .grantType(joinedUser)
                .accessToken(
                        makeAccessToken(
                                new Date(now.getTime() + expiredAt.toMillis()), user))
                .refreshToken(
                        makeRefreshToken(
                                new Date(now.getTime() + expiredAtRefreshToken.toMillis())))
                .build();
    }

    private String makeAccessToken(Date expiry, UserEntity user) {
        Date now = new Date();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .setSubject(user.getEmail())
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();
    }


    private String makeRefreshToken(Date expiry) {
        Date now = new Date();

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecretKey())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .compact();

    }





    public boolean validToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .parseClaimsJws(token);
            if (!claimsJws.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.getSubject(),
                "", authorities), token, authorities);

    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }


    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public String validateAndGetUserId(String token) {
        return getClaims(token).getSubject();
    }


}
