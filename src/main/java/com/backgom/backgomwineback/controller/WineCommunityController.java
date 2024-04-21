package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.service.WineCommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/WineCommunity")
public class WineCommunityController {

    public final WineCommunityService wineCommunityService;

    @GetMapping("/myJoinedClubList")
    public ResponseEntity<?> getMyCommunityList(@RequestParam("email") String email) {
        List<JoinedClubListDto> theJoinedClubList = wineCommunityService
                .getTheJoinedClubList(email);
        return ResponseEntity.ok().body(theJoinedClubList);
    }

    @GetMapping("/myClubList")
    public ResponseEntity<?> getMyCommunityList(@ModelAttribute ClubListDto clubListDto) {
        List<ClubListDto> clubListDtos = wineCommunityService.getClubList(clubListDto);
        return ResponseEntity.ok().body(clubListDtos);
    }
}
