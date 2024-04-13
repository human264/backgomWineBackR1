package com.backgom.backgomwineback.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserRegistrationDto {
    private String email;
    private String password;
    private String phoneNumber;
    private List<MultipartFile> pictures = new ArrayList<>();
    private String emailValidKey;
}