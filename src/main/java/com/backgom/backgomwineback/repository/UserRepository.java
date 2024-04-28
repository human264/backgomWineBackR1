package com.backgom.backgomwineback.repository;


import com.backgom.backgomwineback.domain.User.UserEntity;
import com.backgom.backgomwineback.dto.JoinInDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mapper
public interface UserRepository {
    UserEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);
    int save(@Param("param") UserEntity userEntity);
    Optional<UserEntity> findById( UUID userId);
    Optional<UserEntity> findUserEntityById(UUID userId);

    void savePicturesInUserDetail(
            @Param("email") String email,
            @Param("targetLocationPath") String targetLocationPath);

    List<String> getUserPictures(String email);

    String getUserBasePicture(String email);

    void saveUserInfo(@Param("dto") JoinInDto joinInDto);
}
