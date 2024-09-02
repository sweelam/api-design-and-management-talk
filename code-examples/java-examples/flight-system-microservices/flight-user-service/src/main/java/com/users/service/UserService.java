package com.users.service;

import com.users.dto.UserDto;
import com.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  User authenticate(String username, String password);

  Optional<UserDto> addUser(UserDto userDto);

  UserDto getUserById(Integer userId);

  Optional<UserDto> getUserByEmail(String email);

  List<UserDto> getUsers();

}
