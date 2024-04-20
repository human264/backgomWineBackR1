package com.backgom.backgomwineback.repository;


import com.backgom.backgomwineback.domain.Club.JoinedClubList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JoinedClubListRepository {

    List<JoinedClubList> getTheJoinedClubList(String clubId);
    List<Long> getTheJoinedClubID(String email);





}
