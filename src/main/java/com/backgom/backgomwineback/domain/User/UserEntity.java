package com.backgom.backgomwineback.domain.User;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Users")
public class UserEntity {

    private UUID id;

    private String email;

    private String password;

    private String phoneNumber;

    private String role;

    private String authProvider;

}
