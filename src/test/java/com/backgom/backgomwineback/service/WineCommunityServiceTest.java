package com.backgom.backgomwineback.service;

import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WineCommunityServiceTest {

    @Autowired
    public WineCommunityService wineCommunityService;


    @Test
    public void test(){
        List<JoinedClubListDto> theJoinedClubList = wineCommunityService.getTheJoinedClubList("human264@gmail.com");

        theJoinedClubList.forEach(System.out::println);
    }

    @Test
    public void clubCreateTest() {
        wineCommunityService.createClub(
                ClubListDto.builder()
                        .clubSubject("club 이름 7")
                        .createUser("human264@gmail.com")
                        .build());
    }

}