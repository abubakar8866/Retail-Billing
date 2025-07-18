package com.abubakar.billingSoftware.service;

import java.util.List;

import com.abubakar.billingSoftware.io.UserRequest;
import com.abubakar.billingSoftware.io.UserResponse;

public interface UserService {
    
    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}
