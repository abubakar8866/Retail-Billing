package com.abubakar.billingSoftware.service;

import com.abubakar.billingSoftware.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService {
    
    RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}
