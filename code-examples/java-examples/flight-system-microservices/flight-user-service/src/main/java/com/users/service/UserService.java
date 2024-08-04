package com.users.service;

import com.users.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> addUser(UserDto userDto);
    UserDto getUserById(Integer userId);
    Optional<UserDto> getUserByEmail(String email);
    List<UserDto> getUsers();

    String generateJwt(String username, String password);
}
