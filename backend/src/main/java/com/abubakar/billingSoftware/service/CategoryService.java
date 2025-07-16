package com.abubakar.billingSoftware.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.abubakar.billingSoftware.io.CategoryRequest;
import com.abubakar.billingSoftware.io.CategoryResponse;

public interface CategoryService {
    
    CategoryResponse add(CategoryRequest request,MultipartFile file);

    List<CategoryResponse> read();

    void delete(String categoryId);

}
