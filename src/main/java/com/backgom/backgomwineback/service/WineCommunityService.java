package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.domain.Club.*;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.dto.club.WineInfoDto;
import com.backgom.backgomwineback.dto.club.wineMeetingJoinDto.MeetingJoinTableDto;
import com.backgom.backgomwineback.dto.club.wineMeetingJoinDto.MeetingTableDto;
import com.backgom.backgomwineback.repository.club.ClubListRepository;
import com.backgom.backgomwineback.repository.club.ClubMeetingHandleRepository;
import com.backgom.backgomwineback.repository.club.JoinedClubListRepository;
import com.backgom.backgomwineback.repository.club.WineInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WineCommunityService {

    @Value("${imagePath.clubMain}")
    public String imagePath;

    private final ClubListRepository clubListRepository;
    private final JoinedClubListRepository joinedClubListRepository;
    private final ClubMeetingHandleRepository clubMeetingHandleRepository;
    private final WineInfoRepository wineInfoRepository;

    public List<JoinedClubListDto> getTheJoinedClubList(String email) {


        List<Long> joinedClubID = joinedClubListRepository.getTheJoinedClubID(email);

        if (joinedClubID.isEmpty()) {
            return new ArrayList<>();
        }

        List<ClubList> clubLists = clubListRepository.getClubListByClubId(joinedClubID);

        return clubLists.stream().map(it -> JoinedClubListDto
                .builder()
                .id(it.getId())
                .clubSubject(it.getClubsubject())
                .clubUrl(it.getClubUrl())
                .clubMemberNo(it.getMemberno())
                .location(it.getLocation())
                .isTheJungMoExists(it.isJungmoExists())
                .isTheBunGaeExists(it.isBungaeExists())
                .point(it.getPoint())
                .clubImagePath(it.getClubImagePath())
                .build()).collect(Collectors.toList());
    }

    public List<ClubListDto> getClubList(ClubListDto clubListDto) {

        List<ClubListDto> clubListDtos = new ArrayList<>();
        List<ClubList> clubLists = clubListRepository.getClubLists(clubListDto);
        clubLists.forEach(it -> clubListDtos.add(ClubListDto.entityToDto(it)));

        return clubListDtos;
    }

    public int createClub(ClubListDto build) {
        if (clubListRepository.isTheClubNameExists(build.getClubSubject())) {
            throw new RuntimeException("동일 이름의 클럽이 존재 합니다.");
        }
        return clubListRepository.createClub(build);
    }

    public List<ClubList> findTheClubListForJoin(String email) {
        List<ClubList> theClubListForJoin = clubListRepository.findTheClubListForJoin(email);
        return theClubListForJoin;
    }

    public int joinInTheClub(String email, String clubId) {
        return joinedClubListRepository.joinInTheClub(email, clubId);
    }

    public int makeTheMeeting(String email, MeetingTableDto dto) {
        Map<String, String> wineUUid = new HashMap<>();

        for (var wineName : dto.getWineName()) {
            String wineUUIDFromDB = clubMeetingHandleRepository.getUUIDFromWine(wineName);
            wineUUid.put(wineUUIDFromDB, wineName);
        }

        String meetingTableUUID = UUID.randomUUID().toString();

        var tempMeeting = MeetingTable.builder()
                .uuid(meetingTableUUID)
                .subject(dto.getSubject())
                .detail(dto.getDetail())
                .meetingDate(dto.getMeetingDate())
                .meetingTimeStart(dto.getMeetingTime().get(0))
                .meetingTimeEnd(dto.getMeetingTime().get(1))
                .location(dto.getLocation())
                .locatedPoint(dto.getLocatedPoint())
                .jointedCost(dto.getJointedCost())
                .joinCount(dto.getJoinCount())
                .totalJoinCount(dto.getTotalJoinCount())
                .createEmail(email)
                .createdClub(dto.getCreatedClub())
                .build();

        try {
            if ((!wineUUid.isEmpty())) {
                wineUUid.forEach((key, value) -> {
                    var tempMeetingWine = MeetingTableWine.builder()
                            .meetingUUID(meetingTableUUID)
                            .wineName(value)
                            .wineUUID(key)
                            .build();
                    clubMeetingHandleRepository.saveTheMeetingWine(tempMeetingWine);
                });
            }
        } catch (RuntimeException e) {

        }

        boolean isExists = clubMeetingHandleRepository.isTheMeetingExistsByWineClub(dto.getSubject(), dto.getCreatedClub());
        if (isExists) {
            throw new RuntimeException("이미 해당 클럽에 같은 이름의 모임이 존재 합니다.");
        }

        return clubMeetingHandleRepository.createTheWineMeeting(tempMeeting);
    }

    public List<String> getTheWineNameFromList() {
        return clubMeetingHandleRepository.wineTotalList();
    }

    public int inputTheWineInfo(WineInfoDto dto) {
        if (wineInfoRepository.isExistsSameWineName(dto.getName())) {
            throw new RuntimeException("동일한 와인 이름이 이미 있습니다.");
        }
        return wineInfoRepository.inputTheWineInfo(
                WineInfo.builder()
                        .uuid(UUID.randomUUID().toString())
                        .name(dto.getName())
                        .type(dto.getType())
                        .region(dto.getRegion())
                        .winery(dto.getWinery())
                        .vintage(dto.getVintage())
                        .sweetness(dto.getSweetness())
                        .acidity(dto.getAcidity())
                        .alcohol(dto.getAlcohol())
                        .body(dto.getBody())
                        .density(dto.getDensity())
                        .tannins(dto.getTannins())
                        .anoma(dto.getAnoma())
                        .build()
        );
    }

    public List<MeetingTableDto> getTheMeetingList(String clubName, String email) {
        List<MeetingTable> theWineMeetingList = clubMeetingHandleRepository.getTheWineMeetingList(clubName);
        System.out.println(theWineMeetingList);
        List<MeetingTableDto> meetingTableDtos = new ArrayList<>();
        theWineMeetingList.forEach(it -> {
            meetingTableDtos.add(MeetingTableDto.builder()
                    .uuid(it.getUuid())
                    .subject(it.getSubject())
                    .detail(it.getDetail())
                    .wineName(getTheJoinedWineList(it.getUuid()))
                    .meetingDate(it.getMeetingDate())
                    .meetingTimeStart(it.getMeetingTimeStart())
                    .meetingTimeEnd(it.getMeetingTimeEnd())
                    .location(it.getLocation())
                    .locatedPoint(it.getLocatedPoint())
                    .jointedCost(it.getJointedCost())
                    .joinCount(it.getJoinCount())
                    .createdClub(it.getCreatedClub())
                            .isCanJoin(!clubMeetingHandleRepository.isTheJoinedMeetingExists(email, it.getUuid()))
                    .build());
        });

        return meetingTableDtos;


    }

    public int insertTheCLubMeeting(String meetingUuid, String authentication) {

        if(clubMeetingHandleRepository.isTheJoinedMeetingExists(authentication, meetingUuid)) {
            return 0;
        };

        return clubMeetingHandleRepository
                .insertTheCLubMeeting(MeetingJoinTableDto.builder()
                        .uuid(UUID.randomUUID().toString())
                        .email(authentication)
                        .meetingUuid(meetingUuid)
                        .build());
    }

    public List<JoinedWineMeeting> getTheJoinedMeetingList(String email) {
        return joinedClubListRepository.getTheJoinedWineMeeting(email);
    }

    public List<String> getTheJoinedWineList(String uuid) {
        return clubMeetingHandleRepository.getThePickedWineByClub(uuid);
    }


}
