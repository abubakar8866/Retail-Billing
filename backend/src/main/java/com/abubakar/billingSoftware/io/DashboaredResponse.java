package com.abubakar.billingSoftware.io;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboaredResponse {
    
    private Double todaySales;
    private Long todayOrderCount;
    private List<OrderResponse> recentOrders;

}
