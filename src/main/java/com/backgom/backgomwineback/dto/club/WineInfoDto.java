package com.backgom.backgomwineback.dto.club;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WineInfoDto {
    private String name;
    private String type;
    private String region;
    private String winery;
    private String vintage;
    private Double sweetness;
    private Double acidity;
    private Double alcohol;
    private Double body;
    private Double density;
    private Double tannins;
    private String anoma;
}
