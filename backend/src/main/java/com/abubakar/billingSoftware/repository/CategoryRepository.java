package com.abubakar.billingSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abubakar.billingSoftware.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    
}
