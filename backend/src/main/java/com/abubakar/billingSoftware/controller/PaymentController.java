package com.abubakar.billingSoftware.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abubakar.billingSoftware.io.OrderResponse;
import com.abubakar.billingSoftware.io.PaymentRequest;
import com.abubakar.billingSoftware.io.PaymentVerificationRequest;
import com.abubakar.billingSoftware.io.RazorpayOrderResponse;
import com.abubakar.billingSoftware.service.OrderService;
import com.abubakar.billingSoftware.service.RazorpayService;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    
    private final RazorpayService razorpayService;
    private final OrderService orderService;

    @PostMapping("/create-order")
    @ResponseStatus(HttpStatus.CREATED)
    public RazorpayOrderResponse createRazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException{
        return razorpayService.createOrder(request.getAmount(), request.getCurrency());
    }
        
    @PostMapping("/verify")
    public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request){
        return orderService.verifyPayment(request);
    }
}   
