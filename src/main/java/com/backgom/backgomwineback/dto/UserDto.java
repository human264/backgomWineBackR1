package com.backgom.backgomwineback.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private String token;
    private String email;
    private String password;
    private String phoneNumber;
    private String emailValidKey;
    private UUID id;
}