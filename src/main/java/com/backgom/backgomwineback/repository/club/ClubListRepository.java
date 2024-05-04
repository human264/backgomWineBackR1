package com.backgom.backgomwineback.repository.club;



import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.ClubListToJoinDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClubListRepository {

    List<ClubList> getClubListByClubId(List<Long> clubId);
    List<ClubList> getClubLists(@Param("dto") ClubListDto clubListDto);

    int createClub(@Param("dto") ClubListDto clubListDto);

    boolean isTheClubNameExists(String clubName);

    List<ClubList> findTheClubListForJoin(String email);

}
