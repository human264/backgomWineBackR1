package com.backgom.backgomwineback.service;

import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.domain.User.RefreshToken;
import com.backgom.backgomwineback.domain.User.UserEntity;
import com.backgom.backgomwineback.dto.LogInUser;
import com.backgom.backgomwineback.dto.TokenDto;
import com.backgom.backgomwineback.dto.UserDto;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import com.backgom.backgomwineback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    @Value("${imagePath.userPicture}")
    public String imagePath;

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    public int create(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("Invalid Arguments");
        }
        final String email = userEntity.getEmail();

        if (userRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw new RuntimeException("Email already exists");
        }

        int saved = userRepository.save(userEntity);

        if (saved == 0){
            throw new RuntimeException("가입시 오류 발생");
        }

        return saved;
    }

    public UserEntity getByCredentials(final String email, final String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public UserEntity findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected User"));
    }

    public TokenDto createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        UUID userId = refreshTokenService.findByRefreshToken(refreshToken)
                .getUserId();

        Optional<UserEntity> byId = userRepository.findUserEntityById(userId);
        if (byId.isEmpty()) {
            throw new RuntimeException("User를 찾을 수 없습니다.");
        }
        UserEntity user = byId.get();

        return tokenProvider.generateToken(user, Duration.ofHours(2), Duration.ofDays(1), "user");

    }


    public UserDto refreshTokenSave(UserEntity user) {
        final TokenDto token = tokenProvider
                .generateToken(user, Duration.ofMinutes(30), Duration.ofDays(1), "user");
        final UserDto responseUserDTO = UserDto.builder()
                .email(user.getEmail())
                .id(user.getId())
                .tokenDto(token)
                .build();

        if (refreshTokenRepository.existsByUserId(responseUserDTO.getId())) {
            refreshTokenRepository.deleteByUserId(responseUserDTO.getId());
        }

        RefreshToken refreshToken = new RefreshToken(responseUserDTO.getId(), token.getRefreshToken());
        refreshTokenRepository.save(refreshToken);
        return responseUserDTO;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        LogInUser logInUser = new LogInUser();
        logInUser.setLogInInfo(userEntity);
        return logInUser;
    }

    public void registerPictures(MultipartFile[] files, String email) {

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = now.format(formatter);


        Path rootLocation = Paths.get(imagePath);
        Path dateDirectory = rootLocation.resolve(formattedDate);

        try {
            Files.createDirectories(dateDirectory);  // 폴더 생성
        } catch (IOException e) {
            throw new RuntimeException("Failed to create directory");
        }

        for (MultipartFile file : files) {
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String filename = UUID.randomUUID().toString() + fileExtension;
            try {
                Path targetLocation = dateDirectory.resolve(filename);
                file.transferTo(targetLocation);  // 파일 저장
                log.info("Saved file: {}", targetLocation);

                userRepository.savePicturesInUserDetail(email, targetLocation.toString());

            } catch (IOException e) {
                log.error("Failed to store file: {}", filename, e);
                throw new RuntimeException("Failed to store file " + filename, e);
            }
        }
    }

    private String getFileExtension(String filename) {
        return filename.lastIndexOf(".") != -1 ? filename.substring(filename.lastIndexOf(".")) : "";
    }

    public List<String> getUserPicture(String email) {
        return userRepository.getUserPictures(email);
    }

    public List<Resource> getUserPictureFromFileAddress(List<String> locations) {

        List<Resource> resources = new ArrayList<>();

        for (String location : locations) {
            try {
                Path filePath = Paths.get(location);
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() || resource.isReadable()) {
                    resources.add(resource);
                } else {
                    throw new FileSystemNotFoundException("파일을 찾을 수 없습니다.");
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return resources;
    }


    public String getUserBasePicture(String email) {
        return userRepository.getUserBasePicture(email);
    }
}
