package com.abubakar.billingSoftware.service;

import java.time.LocalDate;
import java.util.List;


import com.abubakar.billingSoftware.io.OrderRequest;
import com.abubakar.billingSoftware.io.OrderResponse;
import com.abubakar.billingSoftware.io.PaymentVerificationRequest;

public interface OrderService {
    
    OrderResponse createOrder(OrderRequest request);

    void deleteUser(String orderId);

    List<OrderResponse> getLatestOrders();

    OrderResponse verifyPayment(PaymentVerificationRequest request);

    Double sumSalesByDate(LocalDate date);

    Long countByOrderDate(LocalDate date);

    List<OrderResponse> findRecentOrders();
}
