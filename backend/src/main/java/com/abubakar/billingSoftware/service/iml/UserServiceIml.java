package com.abubakar.billingSoftware.service.iml;

import java.util.List;

import com.abubakar.billingSoftware.io.UserRequest;
import com.abubakar.billingSoftware.io.UserResponse;
import com.abubakar.billingSoftware.service.UserService;

public class UserServiceIml implements UserService {

    @Override
    public UserResponse createUser(UserRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public String getUserRole(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserRole'");
    }

    @Override
    public List<UserResponse> readUsers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readUsers'");
    }

    @Override
    public void deleteUser(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }
    
}
