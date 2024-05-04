package com.backgom.backgomwineback.domain.Club;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinedWineMeeting {

    private String uuid;
    private String subject;
}
