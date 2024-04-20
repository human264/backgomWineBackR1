package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.config.SecurityConfig;
import com.backgom.backgomwineback.config.TokenProvider;
import com.backgom.backgomwineback.repository.RefreshTokenRepository;
import com.backgom.backgomwineback.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService; // UserService 모킹

    @MockBean
    private TokenProvider tokenProvider; // TokenProvider 모킹

    @MockBean
    private RefreshTokenRepository refreshTokenRepository; // RefreshTokenRepository 모킹 추가

    @Test
    public void testUploadUserPicture() throws Exception {
        byte[] content = Files.readAllBytes(Paths.get("/Users/backgom-air/Downloads/정호연-.jpg"));
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "정호연-.jpg",
                "image/jpeg",
                content
        );

        String email = "test@example.com";
        mockMvc.perform(MockMvcRequestBuilders.multipart("/auth/uploadUserPicture")
                        .file(file)
                        .param("email", email)
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }
}