package com.backgom.backgomwineback.dto.club.wineMeetingJoinDto;


import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingJoinTableDto {

    String uuid;
    String email;
    String meetingUuid;

}
