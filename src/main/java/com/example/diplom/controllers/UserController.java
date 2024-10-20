package com.example.diplom.controllers;

import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.request.UserToAddressRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    @Operation(summary = "Получить список пользователей по потребителю или поставщику")
    public Page<UserInfoResponse> getAllUsers(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer perPage,
                                              @RequestParam(defaultValue = "lastName") String sort,
                                              @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                              @RequestParam(required = false) String filter) {
        return userService.getAllUsers(page,perPage, sort,order,filter);
    }
    @GetMapping("/allToAddress/{id}")
    @Operation(summary = "Получить список пользователей по адресу")
    public List<UserInfoResponse> getAllUsersToAddress(@PathVariable Long id){
        return userService.getAllUsersToAddress(id);
    }

    @PostMapping("/userToAddress")
    @Operation(summary = "Связать пользователя и адрес")
    public void addUserToAddress(@RequestBody @Valid UserToAddressRequest request){
        userService.addUserToAddress(request);
    }
}
