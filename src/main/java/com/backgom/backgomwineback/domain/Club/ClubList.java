package com.backgom.backgomwineback.domain.Club;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("ClubList")

public class ClubList implements Serializable {
    private Long id;

    private String clubsubject;

    private Integer memberno;

    private Integer jungmo;

    private Integer bungae;

    private String location;

    private Double point;

    private String createuser;

    private String operationteam;

    private Date createdate;

    private Date updatedate;

    private static final long serialVersionUID = 1L;


    public Boolean isJungmoExists() {
        if (jungmo == 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBungaeExists() {
        if (bungae == 0) {
            return false;
        } else {
            return true;
        }
    }
}