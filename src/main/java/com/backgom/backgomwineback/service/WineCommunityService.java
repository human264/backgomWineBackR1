package com.backgom.backgomwineback.service;


import com.backgom.backgomwineback.domain.Club.ClubList;
import com.backgom.backgomwineback.domain.Club.JoinedClubList;
import com.backgom.backgomwineback.dto.club.ClubListDto;
import com.backgom.backgomwineback.dto.club.JoinedClubListDto;
import com.backgom.backgomwineback.repository.ClubListRepository;
import com.backgom.backgomwineback.repository.JoinedClubListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class WineCommunityService {

    @Value("${imagePath.clubMain}")
    public String imagePath;

    private final ClubListRepository clubListRepository;
    private final JoinedClubListRepository joinedClubListRepository;


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
                .clubMemberNo(it.getMemberno())
                .isTheJungMoExists(it.isJungmoExists())
                .isTheBunGaeExists(it.isBungaeExists())
                .location(it.getLocation())
                .build()).collect(Collectors.toList());
    }


    public List<ClubListDto> getClubList(ClubListDto clubListDto) {

        List<ClubListDto> clubListDtos = new ArrayList<>();

        List<ClubList> clubLists = clubListRepository.getClubLists(clubListDto);

        clubLists.forEach(it -> clubListDtos.add(ClubListDto.entityToDto(it)));

        return clubListDtos;
    }

    public int createClub(ClubListDto build) {
        return clubListRepository.createClub(build);
    }
}
