package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.dto.JoinInDto;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.service.WineCommunityService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/WineCommunity")
public class WineCommunityController {

    public final WineCommunityService wineCommunityService;

    @GetMapping("/myJoinedClubList")
    public ResponseEntity<?> getMyCommunityList(Authentication authentication) {
        List<JoinedClubListDto> theJoinedClubList = wineCommunityService
                .getTheJoinedClubList(authentication.getName());
        return ResponseEntity.ok().body(theJoinedClubList);
    }

    @GetMapping("/myClubList")
    public ResponseEntity<?> getMyCommunityList(@ModelAttribute ClubListDto clubListDto) {
        List<ClubListDto> clubListDtos = wineCommunityService.getClubList(clubListDto);
        return ResponseEntity.ok().body(clubListDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<?> postCreateMyCommunityList(@RequestBody ClubListDto clubListDto
    , Authentication authentication) {
        if(authentication.getName() == null) {
            throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
        }
        int rows = wineCommunityService.createClub(clubListDto);
        return ResponseEntity.ok().build();
    }


}
