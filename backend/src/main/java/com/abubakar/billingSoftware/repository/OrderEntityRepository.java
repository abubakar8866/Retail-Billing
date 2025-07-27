package com.abubakar.billingSoftware.repository;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abubakar.billingSoftware.entity.OrderEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long> {

    Optional<OrderEntity> findByOrderId(String orderId);

    List<OrderEntity> findAllByOrderByCreatedAtDesc();

    @Query(value = "SELECT SUM(o.grand_total) FROM tbl_orders o WHERE DATE(o.created_at) = :date", nativeQuery = true)
    Double sumSalesByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT COUNT(*) FROM tbl_orders o WHERE DATE(o.created_at) = :date", nativeQuery = true)
    Long countByOrderDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM tbl_orders ORDER BY created_at DESC",nativeQuery = true)
    List<OrderEntity> findRecentOrders(Pageable pageable);
    
}
