package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Tag(name = "Пользователи")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public UserInfoResponse createUser(@RequestBody @Valid UserInfoRequest request){
        return userService.createUser(request);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по id")
    public UserInfoResponse getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по id")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest request){
        return userService.updateUser(id,request);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по id")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
    @GetMapping("/all")
    @Operation(summary = "Получить список пользователей")
    public List<UserInfoResponse> getAllUsers(){
        return userService.getAllUsers();
    }
}
