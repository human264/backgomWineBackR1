package com.backgom.backgomwineback.dto;


import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Alias("JoinInDto")
public class JoinInDto {
    private String email;
    private String password;
    private String phoneNumber;
    private String emailApprovalKey;
    private MultipartFile[] files; // 파일을 추가
}
