package com.backgom.backgomwineback.domain.Club;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingTable {
    private Long id;
    private String uuid;
    private String subject;
    private String detail;
    private String winenameUuid;
    private Date meetingDate;
    private Date meetingTimeStart;
    private Date meetingTimeEnd;
    private String location;
    private String locatedPoint;
    private Integer jointedCost;
    private Integer joinCount;
    private Integer totalJoinCount;
    private String createdClub;
    private String createEmail;
    private Date created_at;
    private Date updated_at;
}
