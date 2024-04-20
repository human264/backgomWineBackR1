package com.backgom.backgomwineback.dto.club;


import com.backgom.backgomwineback.domain.Club.ClubList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubListDto {
    private Long id;
    private String clubSubject;
    private Integer memberNo;
    private Boolean jungmo;
    private Boolean bungae;
    private String location;
    private Double point;
    private String createUser;
    private String operationTeam;
    private String clubImagePath;
    private Date createDate;
    private Date updateDate;

    public static ClubListDto entityToDto(ClubList clubList) {
        return ClubListDto.builder()
                .id(clubList.getId())
                .clubSubject(clubList.getClubsubject())
                .memberNo(clubList.getMemberno())
                .jungmo(getBoolToJungmo(clubList.getJungmo()))
                .bungae(getBoolToBungae(clubList.getBungae()))
                .location(clubList.getLocation())
                .point(clubList.getPoint())
                .createUser(clubList.getCreateuser())
                .operationTeam(clubList.getOperationteam())
                .clubSubject(clubList.getClubsubject())
                .createDate(clubList.getCreatedate())
                .updateDate(clubList.getUpdatedate())
                .build();
    }

    private static Boolean getBoolToBungae(Integer bungae) {
        return bungae == 1 ? true : false;
    }

    private static Boolean getBoolToJungmo(Integer jungmo) {
        return jungmo == 1 ? true : false;
    }


}
