package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.domain.RefreshToken;
import com.backgom.backgomwineback.domain.UserEntity;
import com.backgom.backgomwineback.dto.ResponseDTO;
import com.backgom.backgomwineback.dto.TokenDto;
import com.backgom.backgomwineback.dto.UserDto;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import com.backgom.backgomwineback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    public final UserService userService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            if (userDto == null || userDto.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }
            UserEntity user = UserEntity.builder()
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .build();

            UserEntity registeredUser = userService.create(user);

            UserDto responseuserDTO = UserDto.builder()
                    .id(registeredUser.getId())
                    .email(registeredUser.getEmail())
                    .build();

            return ResponseEntity.ok().body(responseuserDTO);

        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto) {
        UserEntity user = userService.getByCredentials(userDto.getEmail(), userDto.getPassword());
        if (user != null) {
            UserDto responseUserDTO = userService.refreshTokenSave(user);

            return ResponseEntity.ok().body(responseUserDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity
                    .badRequest().body(responseDTO);
        }
    }


}
