package com.backgom.backgomwineback.dto.club;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class JoinedClubListDto {
    private Long id;
    private String clubSubject;
    private int clubMemberNo;
    private boolean isTheJungMoExists;
    private boolean isTheBunGaeExists;
    private String location;

}
