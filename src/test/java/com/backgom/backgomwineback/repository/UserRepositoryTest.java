package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.User.UserEntity;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    public UserRepository userRepository;

    @Test
    void findByEmail() {

        UserEntity byEmail = userRepository.findByEmail("human262@gmail.com");
        System.out.println(byEmail);
    }

    @Test
    void existsByEmail() {
        Boolean byEmail = userRepository.existsByEmail("human262@gmail.com");
        Assert.isTrue(byEmail);
    }

    @Test
    void findByEmailAndPassword() {
        UserEntity byEmailAndPassword = userRepository.findByEmailAndPassword("human262@gmail.com", "12345");
        System.out.println(byEmailAndPassword);
    }

    @Test
    void save() {

        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .email("human262@gmail.com")
                .password("12345")
                .phoneNumber("010-1234-5678")
                .build();
        userRepository.save(userEntity);
    }

    @Test
    void findById() {

        UUID uuid = UUID.fromString("368cc861-cf09-4903-990a-f2dcc9d26fff");
        Optional<UserEntity> byId = userRepository.findById(uuid);
        System.out.println(byId.get());
    }

    @Test
    void findUserEntityById() {

        userRepository.savePicturesInUserDetail("hi.hi.com", "erer");
    }

    @Test
    void fidnUserPictureTest() {
        List<String> userPictures = userRepository.getUserPictures("human264@naver.com");
        userPictures.forEach(System.out::println);
    }
}