package com.backgom.backgomwineback.repository;



import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClubListRepository {

    List<ClubList> getClubListByClubId(List<Long> clubId);
    List<ClubList> getClubLists(@Param("dto") ClubListDto clubListDto);

    int createClub(@Param("dto") ClubListDto build);

}
