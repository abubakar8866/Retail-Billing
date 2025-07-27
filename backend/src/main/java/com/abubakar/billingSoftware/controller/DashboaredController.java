package com.abubakar.billingSoftware.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abubakar.billingSoftware.io.DashboaredResponse;
import com.abubakar.billingSoftware.io.OrderResponse;
import com.abubakar.billingSoftware.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboaredController {
    
    private final OrderService orderService;

    @GetMapping
    public DashboaredResponse getDashboaredData(){
        LocalDate today = LocalDate.now();
        Double todaySale = orderService.sumSalesByDate(today);
        Long todayOrderCount = orderService.countByOrderDate(today);
        List<OrderResponse> recentOrders = orderService.findRecentOrders();
        return new DashboaredResponse(
            todaySale != null ? todaySale : 0.0,
            todayOrderCount != null ? todayOrderCount : 0,
            recentOrders
        );
    }
}
