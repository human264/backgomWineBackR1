package com.backgom.backgomwineback.repository.club;

import com.backgom.backgomwineback.domain.Club.MeetingTable;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

class ClubMeetingHandleRepositoryTest {

    @Autowired
    public ClubMeetingHandleRepository clubMeetingHandleRepository;


    @Test
    void inputtest() {
        MeetingTable meetingTable = new MeetingTable();
        meetingTable.setCreatedClub(UUID.randomUUID().toString());

        clubMeetingHandleRepository.createTheWineMeeting(meetingTable
             );

    }

}