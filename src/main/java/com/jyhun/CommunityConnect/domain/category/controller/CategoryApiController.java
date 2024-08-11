package com.jyhun.CommunityConnect.domain.category.controller;

import com.jyhun.CommunityConnect.domain.category.dto.CategoryRequestDTO;
import com.jyhun.CommunityConnect.domain.category.dto.CategoryResponseDTO;
import com.jyhun.CommunityConnect.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getCategories(){
        List<CategoryResponseDTO> categories = categoryService.findCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/api/categories")
    public ResponseEntity<CategoryResponseDTO> postCategory(@RequestBody CategoryRequestDTO categoryRequestDTO){
        CategoryResponseDTO categoryResponseDTO = categoryService.addCategory(categoryRequestDTO);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/api/categories/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> patchCategory(@RequestBody CategoryRequestDTO categoryRequestDTO,
                                                             @PathVariable Long categoryId){
        CategoryResponseDTO categoryResponseDTO = categoryService.modifyCategory(categoryRequestDTO, categoryId);
        return new ResponseEntity<>(categoryResponseDTO,HttpStatus.OK);
    }

    @DeleteMapping("/api/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId){
        categoryService.removeCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
