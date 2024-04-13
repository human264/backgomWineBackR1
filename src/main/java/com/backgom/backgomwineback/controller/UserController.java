package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.domain.User;
import com.backgom.backgomwineback.dto.UserRegistrationDto;
import com.backgom.backgomwineback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        // 파일을 서버에 저장하는 로직 구현
        List<String> filePaths = new ArrayList<>();
        if (registrationDto.getPictures() != null) {
            for (MultipartFile file : registrationDto.getPictures()) {
                String path = saveFile(file, registrationDto.getEmail());  // saveFile은 파일을 저장하고 경로를 반환하는 메서드
                filePaths.add(path);
            }
        }

        // 파일 경로를 User 엔티티에 저장
        User registered = userService.registerNewUserAccount(registrationDto.getEmail(), registrationDto.getPassword(), filePaths);
        return ResponseEntity.ok(registered);
    }

    private String saveFile(MultipartFile file, String email) {
        try {
            // 날짜 포맷 설정 (예: 2016-03-14)
            String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

            // 저장 경로 설정
            Path directoryPath = Paths.get("/home/backgom/backgomwine", email, "joinin", date);

            // 디렉토리가 존재하지 않으면 생성
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // UUID 생성
            String uuid = UUID.randomUUID().toString();

            // 파일명 설정: UUID_원본 파일명.확장자
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String filename = uuid + "_" + originalFilename.substring(0, originalFilename.lastIndexOf(".")) + fileExtension;

            // 파일 저장 경로
            Path filePath = directoryPath.resolve(filename);

            // 파일 저장
            file.transferTo(filePath);

            // 저장된 파일의 경로 반환
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }
}