package com.backgom.backgomwineback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private TokenDto tokenDto;
    private String email;
    private String password;
    private String phoneNumber;
    private String emailValidKey;
    private UUID id;
}