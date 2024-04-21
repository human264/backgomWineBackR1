package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.domain.User.UserEntity;
import com.backgom.backgomwineback.dto.ResponseDTO;
import com.backgom.backgomwineback.dto.UserDto;
import com.backgom.backgomwineback.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    public final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        try {
            if (userDto == null || userDto.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }

            UUID uuid = UUID.randomUUID();
            UserEntity user = UserEntity.builder()
                    .id(uuid)
                    .email(userDto.getEmail())
                    .password(userDto.getPassword())
                    .build();

            int isUserCreated = userService.create(user);

            if (isUserCreated == 0) {
                throw new RuntimeException("User creation failed");
            }

            UserEntity registeredUser = userService.findById(uuid);

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

    @PostMapping("/uploadUserPicture")
    public void registerPictures(@RequestParam("file") MultipartFile[] files,
                                 @RequestParam("email") String email) {
        userService.registerPictures(files, email);
    }

    @GetMapping("/getUserBasePicture")
    public ResponseEntity<Resource> getTheUseBasePicture(Authentication authentication) {

        if (authentication == null) {
            throw new RuntimeException("로그인 안된 상태에서 사진 요청");
        }

        String fileName = userService.getUserBasePicture(authentication.getName());

        try{
            Path file = Paths.get(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                String cleanFilename = file.getFileName().toString();
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + cleanFilename + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}
