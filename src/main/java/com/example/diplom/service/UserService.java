package com.example.diplom.service;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    public UserInfoResponse createUser(UserInfoRequest request) {
        return null;
    }

    public UserInfoResponse getUser(Long id) {
        return null;
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        return null;
    }

    public void deleteUser(Long id) {

    }

    public List<UserInfoResponse> getAllUsers() {
        return null;
    }
}
