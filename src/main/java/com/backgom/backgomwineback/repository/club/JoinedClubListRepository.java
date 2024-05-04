package com.backgom.backgomwineback.repository.club;


import com.backgom.backgomwineback.domain.Club.JoinedClubList;
import com.backgom.backgomwineback.domain.Club.JoinedWineMeeting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JoinedClubListRepository {
    List<JoinedClubList> getTheJoinedClubList(String clubId);
    List<Long> getTheJoinedClubID(String email);
    int joinInTheClub(@Param("email") String email, @Param("clubId") String clubId);

    List<JoinedWineMeeting> getTheJoinedWineMeeting(String email);
}
