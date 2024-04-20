package com.backgom.backgomwineback.repository;

import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.domain.Club.JoinedClubList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}