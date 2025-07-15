package com.abubakar.billingSoftware.service;

import java.util.List;

import com.abubakar.billingSoftware.io.CategoryRequest;
import com.abubakar.billingSoftware.io.CategoryResponse;

public interface CategoryService {
    
    CategoryResponse add(CategoryRequest request);

    List<CategoryResponse> read();

}
