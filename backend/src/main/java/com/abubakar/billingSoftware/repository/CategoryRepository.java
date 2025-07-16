package com.abubakar.billingSoftware.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abubakar.billingSoftware.entity.CategoryEntity;


public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
 
    Optional<CategoryEntity> findByCategoryId(String categoryId);
}
