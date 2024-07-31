package com.jyhun.CommunityConnect.domain.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearchDTO {

    private String searchDateType;
    private String searchBy;
    private String searchQuery;

}
