package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.entity.User;
import com.example.diplom.model.db.repository.UserRepository;
import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.request.UserToAddressRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.model.enums.AttributeUser;
import com.example.diplom.model.enums.UserStatus;
import com.example.diplom.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private AddressService addressService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserInfoResponse result = userService.createUser(request);
        assertEquals(user.getId(), result.getId());
    }
    @Test(expected = CustomException.class)
    public void createUser_badEmail() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@.test.com");

        userService.createUser(request);
    }

    @Test(expected = CustomException.class)
    public void createUser_userExists() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        userService.createUser(request);
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserInfoResponse result = userService.getUser(1L);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void controlStatus() {
        User user = new User();
        user.setId(1L);
        user.setStatus(UserStatus.CREATED);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.controlStatus(user.getId());
        assertEquals(UserStatus.CREATED, user.getStatus());
    }

    @Test(expected = CustomException.class)
    public void controlStatus_badStatus() {
        User user = new User();
        user.setId(1L);
        user.setStatus(UserStatus.DELETED);

        userService.controlStatus(user.getId());
    }


    @Test
    public void updateUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setAttributeUser(AttributeUser.CONSUMER);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateUser(1L, request);
        verify(userRepository,times(1)).save(any(User.class));
        assertEquals(UserStatus.UPDATED, user.getStatus());
        assertEquals(AttributeUser.CONSUMER, user.getAttributeUser());
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(UserStatus.DELETED,user.getStatus());
    }

    @Test
    public void getAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setLastName("Ivanov");
        user.setAttributeUser(AttributeUser.CONSUMER);

        User user1 = new User();
        user1.setId(2L);
        user1.setLastName("Petrov");
        user1.setAttributeUser(AttributeUser.CONSUMER);

        List<User> users = List.of(user, user1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "attributeUser", Sort.Direction.ASC);

        PageImpl<User> page = new PageImpl<>(users, pageRequest,users.size());
        when(userRepository.findByUserStatusAndAttributeUserNot(pageRequest,UserStatus.DELETED,"CONSUMER")).thenReturn(page);

        Page<UserInfoResponse> result = userService.getAllUsers(1,3,"attributeUser", Sort.Direction.ASC,"CONSUMER");
        assertEquals(users.size(),result.getTotalElements());
    }

    @Test
    public void getAllUsers_notFilter() {
        User user = new User();
        user.setId(1L);
        user.setLastName("Ivanov");
        user.setAttributeUser(AttributeUser.CONSUMER);

        User user1 = new User();
        user1.setId(2L);
        user1.setLastName("Petrov");
        user1.setAttributeUser(AttributeUser.CONSUMER);

        List<User> users = List.of(user, user1);
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 3, "attributeUser", Sort.Direction.ASC);

        PageImpl<User> page = new PageImpl<>(users, pageRequest,users.size());
        when(userRepository.findByUserStatusNot(pageRequest,UserStatus.DELETED)).thenReturn(page);

        Page<UserInfoResponse> result = userService.getAllUsers(1,3,"attributeUser", Sort.Direction.ASC,null);
        assertEquals(users.size(),result.getTotalElements());
    }

    @Test
    public void addUserToAddress() {
        User user = new User();
        user.setId(1L);
        user.setAddresses(new ArrayList<>());
        user.setStatus(UserStatus.CREATED);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Address address = new Address();
        address.setId(1L);
        when(addressService.getAddressFromDB(address.getId())).thenReturn(address);
        UserToAddressRequest request = UserToAddressRequest.builder()
                .userId(user.getId())
                .addressId(address.getId())
                .build();
        userService.addUserToAddress(request);
        verify(userRepository,times(1)).save(any(User.class));
        assertEquals(user.getAddresses().get(0).getId(), request.getAddressId());
    }

    @Test
    public void getAllUsersToAddress() {
        Address address = new Address();
        address.setId(1L);
        List<Address> addresses = List.of(address);
        User user1 = new User();
        user1.setId(1L);
        user1.setAddresses(addresses);
        User user2 = new User();
        user2.setId(1L);
        user2.setAddresses(addresses);
        List<User> users = List.of(user1, user2);
        when(userRepository.findUsersByAddresses(address.getId(), UserStatus.DELETED)).thenReturn(users);
       List<UserInfoResponse> result = userService.getAllUsersToAddress(address.getId());


        assertEquals(users.size(), result.size());
    }
}