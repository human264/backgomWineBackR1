package com.backgom.backgomwineback.domain.Club;

import java.io.Serializable;
import java.util.Date;

import com.backgom.backgomwineback.dto.club.ClubListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Alias("ClubList")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubList implements Serializable {
    private Long id;

    private String clubsubject;

    private Integer memberno;

    private Integer jungmo;

    private Integer bungae;

    private String location;

    private Double point;

    private String createuser;

    private String operationteam;

    private Date createdate;

    private Date updatedate;

    private static final long serialVersionUID = 1L;


    public Boolean isJungmoExists() {
        return this.jungmo == 1 ? true : false;
    }

    public boolean isBungaeExists() {
        return this.bungae == 1 ? true : false;
    }


    public static ClubList  dtoToEntity(ClubListDto dto) {
        return ClubList.builder()
                .id(dto.getId())
                .clubsubject(dto.getClubSubject())
                .memberno(dto.getMemberNo())
                .jungmo(getBoolToJungmo(dto.getJungmo()))
                .bungae(getBoolToBungae(dto.getBungae()))
                .location(dto.getLocation())
                .point(dto.getPoint())
                .createuser(dto.getCreateUser())
                .operationteam(dto.getOperationTeam())
                .clubsubject(dto.getClubSubject())
                .createdate  (dto.getCreateDate())
                .updatedate(dto.getUpdateDate())
                .build();
    }

    private static Integer getBoolToBungae(Boolean bungae) {
        return bungae ? 1 : 0;
    }

    private static Integer getBoolToJungmo(Boolean jungmo) {
        return jungmo ? 1 : 0;
    }

}