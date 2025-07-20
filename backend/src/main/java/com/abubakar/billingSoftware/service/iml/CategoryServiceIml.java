package com.abubakar.billingSoftware.service.iml;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abubakar.billingSoftware.entity.CategoryEntity;
import com.abubakar.billingSoftware.io.CategoryRequest;
import com.abubakar.billingSoftware.io.CategoryResponse;
import com.abubakar.billingSoftware.repository.CategoryRepository;
import com.abubakar.billingSoftware.repository.ItemRepository;
import com.abubakar.billingSoftware.service.CategoryService;
import com.abubakar.billingSoftware.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceIml implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;
    private final ItemRepository itemRepository;

    @Override
    public CategoryResponse add(CategoryRequest request,MultipartFile file) {
        String imgUrl = fileUploadService.uploadFile(file);
        CategoryEntity newCategory = convertToEntity(request);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }
                
    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        Integer itemsCount =  itemRepository.countByCategoryEntity_Id(newCategory.getId());
        return CategoryResponse .builder()
            .CategoryId(newCategory.getCategoryId())
            .name(newCategory.getName())
            .description(newCategory.getDescription())
            .bgColor(newCategory.getBgColor())
            .imgUrl(newCategory.getImgUrl())
            .createdAt(newCategory.getCreatedAt())
            .updatedAt(newCategory.getUpdatedAt())
            .items(itemsCount)
            .build();
    }
        
    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
            .categoryId(UUID.randomUUID().toString())
            .name(request.getName())
            .description(request.getDescription())
            .bgColor(request.getBgColor())
            .build();
    }

    @Override
    public List<CategoryResponse> read() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryEntity -> convertToResponse(categoryEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId).orElseThrow(()-> new RuntimeException("Category is not fount "+categoryId));
        fileUploadService.deleteFile(existingCategory.getImgUrl());
        categoryRepository.delete(existingCategory);
    }
    
}
