package com.jyhun.CommunityConnect.domain.category.service;

import com.jyhun.CommunityConnect.domain.category.dto.CategoryRequestDTO;
import com.jyhun.CommunityConnect.domain.category.dto.CategoryResponseDTO;
import com.jyhun.CommunityConnect.domain.category.entity.Category;
import com.jyhun.CommunityConnect.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findCategories(){
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.toDTO(category);
            categoryResponseDTOList.add(categoryResponseDTO);
        }
        return categoryResponseDTOList;
    }

    public CategoryResponseDTO addCategory(CategoryRequestDTO categoryRequestDTO){
        Category category = categoryRequestDTO.toEntity();
        Category savedCategory = categoryRepository.save(category);
        CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.toDTO(savedCategory);
        return categoryResponseDTO;
    }

    public CategoryResponseDTO modifyCategory(CategoryRequestDTO categoryRequestDTO, Long categoryId){
        Category category = categoryRequestDTO.toEntity();
        Category foundCategory = categoryRepository.findById(categoryId).orElseThrow();
        foundCategory.updateCategory(category);
        CategoryResponseDTO categoryResponseDTO = CategoryResponseDTO.toDTO(foundCategory);
        return categoryResponseDTO;
    }

    public void removeCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        categoryRepository.delete(category);
    }

}
