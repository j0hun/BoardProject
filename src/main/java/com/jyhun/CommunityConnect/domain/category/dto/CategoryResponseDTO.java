package com.jyhun.CommunityConnect.domain.category.dto;

import com.jyhun.CommunityConnect.domain.category.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponseDTO {

    private String name;

    public static CategoryResponseDTO toDTO(Category category){
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
        categoryResponseDTO.setName(category.getName());
        return categoryResponseDTO;
    }

}
