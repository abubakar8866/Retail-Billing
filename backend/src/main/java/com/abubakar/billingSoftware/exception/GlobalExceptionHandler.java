package com.abubakar.billingSoftware.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) throws Exception {
        System.err.println("🔥 Unhandled exception: " + e.getMessage());
        e.printStackTrace();
        throw e; // Let Spring Security handle it further
    }
}