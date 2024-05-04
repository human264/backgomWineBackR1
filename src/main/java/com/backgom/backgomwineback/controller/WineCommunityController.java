package com.backgom.backgomwineback.controller;

import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.domain.Club.JoinedWineMeeting;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.dto.club.WineInfoDto;
import com.backgom.backgomwineback.dto.club.wineMeetingJoinDto.MeetingTableDto;
import com.backgom.backgomwineback.service.WineCommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
        if (authentication.getName() == null) {
            throw new RuntimeException("로그인 정보가 존재하지 않습니다.");
        }
        int rows = wineCommunityService.createClub(clubListDto);
        return ResponseEntity.ok().body(rows);
    }

    @GetMapping("/FindToJoin")
    public ResponseEntity<?> getTheClubListForJoin(Authentication authentication) {

        List<ClubList> theClubListForJoin = wineCommunityService.findTheClubListForJoin(authentication.getName());

        return ResponseEntity.ok()
                .body(theClubListForJoin);
    }

    @PostMapping("/ClubJoinIn")
    public ResponseEntity<?> joinInTheClub(
            @RequestBody String clubId,
            Authentication authentication) {
        String email = authentication.getName();
        int joined = wineCommunityService.joinInTheClub(email, clubId);

        return ResponseEntity.ok()
                .body(joined);
    }

    @GetMapping("/getTheWineTotalList")
    public ResponseEntity<?> getTheWineTotalList() {
        List<String> theWineNameFromList = wineCommunityService.getTheWineNameFromList();
        return ResponseEntity.ok().body(theWineNameFromList);
    }

    @PostMapping("/createTheWineMeeting")
    public ResponseEntity<?> createTheWineMeeting(@RequestBody MeetingTableDto dto, Authentication authentication) {
        String email = authentication.getName();
        int i = wineCommunityService.makeTheMeeting(email, dto);
        return ResponseEntity.ok().body(i);
    }

    @PostMapping("/inputTheWineInfo")
    public ResponseEntity<?> inputTheWineInfo(@RequestBody WineInfoDto dto) {
        int reseult = wineCommunityService.inputTheWineInfo(dto);
        return ResponseEntity.ok().body(reseult);
    }

    @GetMapping("/getTheMeetingList")
    public ResponseEntity<?> getTheMeetingList(@RequestParam String clubName, Authentication authentication) {
        String email = authentication.getName();
        List<MeetingTableDto> result = wineCommunityService.getTheMeetingList(clubName, email);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/joinIntheMeeting")
    public ResponseEntity<?> insertTheCLubMeeting(@RequestBody String meetingUuid, Authentication authentication) {
        System.out.println(meetingUuid.length());
        String uuid = meetingUuid.replace("\"", ""); // 서버 측에서 UUID 문자열의 쌍따옴표를 제거
        int result = wineCommunityService.insertTheCLubMeeting(uuid, authentication.getName());

        if (result == 0) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("모임 참석에 실패 하였습니다.");
        }
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/review/wineReview")
    public ResponseEntity<?> getTheJoinedMeetingList(Authentication authentication) {
        List<JoinedWineMeeting> joinedWineMeetingList = wineCommunityService.getTheJoinedMeetingList(authentication.getName());
        return ResponseEntity.ok().body(joinedWineMeetingList);
    }

    @GetMapping("/review/wineReview/{uuid}")
    public ResponseEntity<?> getTheJoinedWineList(@PathVariable String uuid) {
        List<String> theJoinedWineList = wineCommunityService.getTheJoinedWineList(uuid);
        return ResponseEntity.ok().body(theJoinedWineList);
    }
}