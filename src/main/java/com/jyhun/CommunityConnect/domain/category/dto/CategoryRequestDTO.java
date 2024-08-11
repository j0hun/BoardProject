package com.jyhun.CommunityConnect.domain.category.dto;

import com.jyhun.CommunityConnect.domain.category.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {

    private String name;

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }

}
