package com.backgom.backgomwineback.repository;



import com.backgom.backgomwineback.domain.Club.ClubList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClubListRepository {

    List<ClubList> getClubListByClubId(List<Long> clubId);



}
