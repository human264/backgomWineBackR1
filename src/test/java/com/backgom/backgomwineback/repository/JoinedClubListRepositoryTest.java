package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.domain.Club.JoinedClubList;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.repository.club.ClubListRepository;
import com.backgom.backgomwineback.repository.club.JoinedClubListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class JoinedClubListRepositoryTest {

    @Autowired
    JoinedClubListRepository repository;

    @Autowired
    ClubListRepository clubListRepository;

    @Test
    void getJoinedClubList() {
        List<JoinedClubList> theJoinedClubList =
                repository.getTheJoinedClubList("human264@email.com");
        System.out.println("theJoinedClubList = " + theJoinedClubList);

    }

    @Test
    void getJoinedClubListById() {
        List<Long> theJoinedClubID = repository.getTheJoinedClubID("human264@gmail.com");
        List<ClubList> clubListByClubId = clubListRepository.getClubListByClubId(theJoinedClubID);
        clubListByClubId.forEach(System.out::println);
    }

    @Test
    void getJoinedClubListByemail() {
        List<ClubList> theJoinedClubID = clubListRepository.findTheClubListForJoin("human264@gmail.com");
        for (ClubList clubList : theJoinedClubID) {
            System.out.println(clubList);
        }
    }

    @Test
    void InsertClubList() {
        int club = clubListRepository.createClub(ClubListDto.builder()
                .clubSubject("새모임")
                .clubImagePath("ex")
                        .createUser("huma.vne")

                .build());

        System.out.println(club);

    }
}