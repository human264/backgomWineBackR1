package com.backgom.backgomwineback.repository.club;


import com.backgom.backgomwineback.domain.Club.WineInfo;
import com.backgom.backgomwineback.dto.club.WineInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WineInfoRepository {

    int inputTheWineInfo(@Param("dto") WineInfo dto);

    boolean isExistsSameWineName(String wineName);
}
