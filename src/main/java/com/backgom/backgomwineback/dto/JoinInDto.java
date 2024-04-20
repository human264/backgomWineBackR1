package com.backgom.backgomwineback.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class JoinInDto {

    private String email;
    private String password;
    private String phoneNumber;
    private List<MultipartFile> pictures;
    private String emailApprovalKey;

}
