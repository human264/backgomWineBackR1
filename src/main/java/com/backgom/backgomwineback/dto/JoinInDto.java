package com.backgom.backgomwineback.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class JoinInDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String emailApprovalKey;

}
