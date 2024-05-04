package com.backgom.backgomwineback.service;
import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import com.backgom.backgomwineback.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;


@Transactional
class UserServiceTest {


    private final Path rootLocation = Paths.get("/Users/backgom-air/backgomwine");

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenProvider tokenProvider;

    @TempDir
    Path temporaryFolder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        doNothing().when(userRepository).savePicturesInUserDetail(anyString(), anyString());
    }

    @Test
    void testRegisterPictures() throws Exception {

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "정호연-.jpg",
                "image/jpeg",
        Files.readAllBytes(Paths.get("/Users/backgom-air/Downloads/정호연-.jpg")));

        userService.registerPictures(new MultipartFile[]{file}, "1@world.com");

    }

    @Test
    void getUserPictureFromFileAddress_returnsResources() throws IOException {
        // 준비
        List<String> fileLocations = Arrays.asList(
                "/Users/backgom-air/backgomwine/2024-04-20_13-11-33/6c9ef564-acb8-4d40-947d-b2d27fedd504.jpg",
                "/Users/backgom-air/backgomwine/2024-04-19_23-00-49/38b22e79-d20c-4912-bed9-ad9935fb7431.jpg"
        );

        // 실행
        List<Resource> resources = userService.getUserPictureFromFileAddress(fileLocations);

        // 검증
        assertNotNull(resources);
        assertEquals(2, resources.size());
        assertTrue(resources.get(0).exists());
        assertTrue(resources.get(1).exists());
        assertEquals(Paths.get(fileLocations.get(0)).toUri().toString(), resources.get(0).getURI().toString());
        assertEquals(Paths.get(fileLocations.get(1)).toUri().toString(), resources.get(1).getURI().toString());
    }

    @Test
    void getUserPictureFromFileAddress_throwsExceptionWhenFileNotFound() {
        // 준비
        List<String> fileLocations = Arrays.asList(
                "/Users/backgom-air/backgomwine/2024-04-20_13-11-08/another-image.jpg"
        );

        // 실행 & 검증
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserPictureFromFileAddress(fileLocations);
        });

        assertTrue(exception.getCause()
                instanceof java.nio.file.FileSystemNotFoundException);
    }


    @Test
    void maketheUUID() {
        UUID uuid = UUID.randomUUID();

        String uuidString = uuid.toString();

        System.out.println(uuidString);

    }
}