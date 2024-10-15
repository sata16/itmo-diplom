package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserInfoResponse createUser(@RequestBody UserInfoRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/{id}")
    public UserInfoResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @PutMapping("/{id}")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
    @GetMapping("/all")
    public List<UserInfoResponse> getAllUsers(){
        return userService.getAllUsers();
    }
}
