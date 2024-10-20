package com.example.diplom.service;

import com.example.diplom.exceptions.CustomException;
import com.example.diplom.model.db.entity.Address;
import com.example.diplom.model.db.entity.User;
import com.example.diplom.model.db.repository.UserRepository;
import com.example.diplom.model.dto.request.UserInfoRequest;
import com.example.diplom.model.dto.request.UserToAddressRequest;
import com.example.diplom.model.dto.response.UserInfoResponse;
import com.example.diplom.model.enums.UserStatus;
import com.example.diplom.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final AddressService addressService;
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public UserInfoResponse createUser(UserInfoRequest request) {
        validateEmail(request);

        userRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(user->{
                    throw new CustomException(String.format("User with email: %s already exists",request.getEmail()),HttpStatus.BAD_REQUEST);
                });
        User user = mapper.convertValue(request,User.class);

        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.CREATED);

        User save = userRepository.save(user);

        return mapper.convertValue(save,UserInfoResponse.class);
    }

    //проверка валидации email
    private void validateEmail(UserInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw  new CustomException("Invalid email format", HttpStatus.BAD_REQUEST);
        }
    }

    //метод получения пользователя. исключаем дублирование в методах
    private User getUserFromDB(Long id){
        return userRepository.findById(id)
                .orElseThrow(()->new CustomException("Пользователь не найден", HttpStatus.NOT_FOUND));
    }
    //Проверка статуса пользователя
    private void controlStatus(Long id){
        userRepository.findById(id).filter(user -> !user.getStatus().equals(UserStatus.DELETED)).orElseThrow(()->new CustomException("User has status DELETED", HttpStatus.BAD_REQUEST));
    }

    public UserInfoResponse getUser(Long id) {
        User user = getUserFromDB(id);
        return mapper.convertValue(user,UserInfoResponse.class);
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        validateEmail(request);

        User user = getUserFromDB(id);
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword() == null? user.getPassword() : request.getPassword());
        user.setFirstName(request.getFirstName() == null? user.getFirstName() : request.getFirstName());
        user.setLastName(request.getLastName() == null? user.getLastName() : request.getLastName());
        user.setMiddleName(request.getMiddleName() == null? user.getMiddleName() : request.getMiddleName());
        user.setAttributeUser(request.getAttributeUser() == null? user.getAttributeUser() : request.getAttributeUser());

        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.UPDATED);

        User save = userRepository.save(user);

        return mapper.convertValue(save,UserInfoResponse.class);
    }

    public void deleteUser(Long id) {
        User user = getUserFromDB(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);

    }


    public Page<UserInfoResponse> getAllUsers(Integer page, Integer perPage, String sort,
                                          Sort.Direction order, String filter) {

        Pageable pageRequest = PaginationUtil.getPageRequests(page,perPage, sort,order);
        Page<User> all;
        if(filter == null){
            all = userRepository.findByUserStatusNot(pageRequest, UserStatus.DELETED);
        }else{
            all = userRepository.findByUserStatusAndAttributeUserNot(pageRequest,UserStatus.DELETED,filter);
        }
        List<UserInfoResponse> content = all.getContent()
            .stream()
            .map(user -> mapper.convertValue(user, UserInfoResponse.class))
            .collect(Collectors.toList());
        return new PageImpl<>(content,pageRequest, all.getTotalElements());

}

    public void addUserToAddress(UserToAddressRequest request) {
        controlStatus(request.getUserId());
        addressService.controlStatus(request.getAddressId());
        User userFromDB = getUserFromDB(request.getUserId());
        Address addressFromDB = addressService.getAddressFromDB(request.getAddressId());


        userFromDB.getAddresses().add(addressFromDB);
        userRepository.save(userFromDB);

    }

    public List<UserInfoResponse> getAllUsersToAddress(Long id) {

       return userRepository.findUsersByAddresses(id,UserStatus.DELETED).stream()
               .map(user -> mapper.convertValue(user,UserInfoResponse.class))
               .collect(Collectors.toList());


    }


}
