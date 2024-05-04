package com.backgom.backgomwineback.dto.club.wineAfterTalk;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
public class AfterTalkDto {

    private List<String> meeting;
    private String afterTalk;
    private Double totalPoint;
    private MultipartFile[] meetingPhoto;
    private List<String> wineNames;
    private String wineRegion;
    private String wineFactory;
    private Date wineVintage;
    private String wineVariety;
    private Date inputDate;
    private Double sweetness;
    private Double acidity;
    private Double body;
    private Double tannins;
    private Double alcohol;
    private Double totalWinePoint;
    private String wineNumber;
    private String wineColor;
    private String wineFruitSmell;
    private String wineFlowerSmell;
    private Double wineSpacy;
    private String wineHerb;
    private Double wineOak;
    private String other;
    private String detailNote;


}
