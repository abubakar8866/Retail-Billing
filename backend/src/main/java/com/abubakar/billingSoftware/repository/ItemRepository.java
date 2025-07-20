package com.abubakar.billingSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abubakar.billingSoftware.entity.ItemEntity;
import java.util.Optional;


public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
    
    Optional<ItemEntity> findByItemId(String itemId);

    Integer countByCategoryEntity_Id(Long id);

}
