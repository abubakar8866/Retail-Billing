package com.abubakar.billingSoftware.service.iml;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.abubakar.billingSoftware.entity.OrderEntity;
import com.abubakar.billingSoftware.entity.OrderItemEntity;
import com.abubakar.billingSoftware.io.OrderRequest;
import com.abubakar.billingSoftware.io.OrderResponse;
import com.abubakar.billingSoftware.io.PaymentDetails;
import com.abubakar.billingSoftware.io.PaymentMethod;
import com.abubakar.billingSoftware.repository.OrderEntityRepository;
import com.abubakar.billingSoftware.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderserviceIml implements OrderService {

    private final OrderEntityRepository orderEntityRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderEntity newOrder = convertToOrderEntity(request);

        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH ? PaymentDetails.PaymentStatus.COMPLETED : PaymentDetails.PaymentStatus.PENDING);
        newOrder.setPaymentDetails(paymentDetails);

        List<OrderItemEntity> orderItems = request.getCartItems().stream().map(this::convertToOrderItemEntity).collect(Collectors.toList());

        newOrder.setItems(orderItems);

        newOrder = orderEntityRepository.save(newOrder);
        return convertToResponse(newOrder);
    }

    private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest){
        return OrderItemEntity.builder()
            .itemId(orderItemRequest.getItemId())
            .name(orderItemRequest.getName())
            .price(orderItemRequest.getPrice())
            .quantity(orderItemRequest.getQuantity())
            .build();
    }

    private OrderResponse convertToResponse(OrderEntity newOrder){
        return OrderResponse.builder()
            .orderId(newOrder.getOrderId())
            .customerName(newOrder.getCustomerName())
            .phoneNumber(newOrder.getPhoneNumber())
            .subtotal(newOrder.getSubTotal())
            .tax(newOrder.getTax())
            .grandTotal(newOrder.getGrandTotal())
            .paymentMethod(newOrder.getPaymentMethod())
            .items(newOrder.getItems().stream().map(this::convertToItemResponse).collect(Collectors.toList()))
            .paymentDetails(newOrder.getPaymentDetails())
            .createdAt(newOrder.getCreatedAt())
            .build();
    }

    private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity){
        return OrderResponse.OrderItemResponse.builder()
            .itemId(orderItemEntity.getItemId())
            .name(orderItemEntity.getName())
            .price(orderItemEntity.getPrice())
            .quantity(orderItemEntity.getQuantity())
            .build();
    }

    private OrderEntity convertToOrderEntity(OrderRequest request){
        return OrderEntity.builder()
            .customerName(request.getCustomerName())
            .phoneNumber(request.getPhoneNumber())
            .subTotal(request.getSubtotal())
            .tax(request.getTax())
            .grandTotal(request.getGrandTotal())
            .paymentMethod(PaymentMethod.valueOf(request.getPaymentMethod()))
            .build();
    }

    @Override
    public void deleteUser(String orderId) {
        OrderEntity existingOrder = orderEntityRepository.findByOrderId(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found."));
        orderEntityRepository.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getLatestOrders() {
        return orderEntityRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
    }
    
}
