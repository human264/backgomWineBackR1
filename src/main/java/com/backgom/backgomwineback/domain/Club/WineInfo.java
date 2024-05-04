package com.backgom.backgomwineback.domain.Club;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WineInfo {
    private String uuid;
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
