package com.abubakar.billingSoftware.service;

import java.util.List;

import com.abubakar.billingSoftware.io.OrderRequest;
import com.abubakar.billingSoftware.io.OrderResponse;

public interface OrderService {
    
    OrderResponse createOrder(OrderRequest request);

    void deleteUser(String orderId);

    List<OrderResponse> getLatestOrders();
}
