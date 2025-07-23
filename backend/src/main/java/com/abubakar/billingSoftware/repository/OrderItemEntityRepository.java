package com.abubakar.billingSoftware.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abubakar.billingSoftware.entity.OrderItemEntity;

@Repository
public interface OrderItemEntityRepository extends JpaRepository<OrderItemEntity,Long> {
    
}
