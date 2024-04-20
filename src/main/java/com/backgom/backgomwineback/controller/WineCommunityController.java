package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.service.WineCommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/WineCommunity")
public class WineCommunityController {

    public final WineCommunityService wineCommunityService;

    @GetMapping("/`myClubList`")
    public ResponseEntity<?> getMyCommunityList(@Param("email") String email) {

        List<JoinedClubListDto> theJoinedClubList = wineCommunityService
                .getTheJoinedClubList(email);

        return ResponseEntity.ok().body(theJoinedClubList);
    }
}
