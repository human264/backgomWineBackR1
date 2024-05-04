package com.backgom.backgomwineback.dto.club.wineMeetingJoinDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class MeetingTableDto {
    private String uuid;
    private String subject;
    private String detail;
    private List<String> wineName;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date meetingDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private List<Date> meetingTime;
    private Date meetingTimeStart;
    private Date meetingTimeEnd;

    private String location;
    private String locatedPoint;
    private Integer jointedCost;
    private Integer joinCount;
    private Integer totalJoinCount;
    private String createdClub;

    private Boolean isCanJoin;
}

