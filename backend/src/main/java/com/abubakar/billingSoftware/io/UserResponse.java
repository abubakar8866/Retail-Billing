package com.abubakar.billingSoftware.io;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    
    private String userId;
    private String name;
    private String email;
    private String pasaword;
    private String role;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
