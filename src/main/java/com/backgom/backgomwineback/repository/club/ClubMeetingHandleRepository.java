package com.backgom.backgomwineback.repository.club;


import com.backgom.backgomwineback.domain.Club.JoinedWineMeeting;
import com.backgom.backgomwineback.domain.Club.MeetingTable;
import com.backgom.backgomwineback.domain.Club.MeetingTableWine;
import com.backgom.backgomwineback.dto.club.wineMeetingJoinDto.MeetingJoinTableDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface ClubMeetingHandleRepository {

    boolean isTheJoinedMeetingExists(String email, String meetingUUid);

    List<String> wineTotalList();

    int createTheWineMeeting(@Param("dto") MeetingTable meetingTable);

    String getUUIDFromWine(String wineName);

    int saveTheMeetingWine(@Param("dto") MeetingTableWine tempMeetingWine);

    boolean isTheMeetingExistsByWineClub(String subject, String clubName);

    List<MeetingTable> getTheWineMeetingList(String clubName);

    List<String> getThePickedWineByClub(String winenameUuid);

    int insertTheCLubMeeting(@Param("dto") MeetingJoinTableDto dto);


}
