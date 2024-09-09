package com.auth.service.impl;

import com.auth.dto.UserDto;
import com.auth.entity.User;
import com.auth.exceptions.UserApiException;
import com.auth.mappers.UserMapper;
import com.auth.repo.UserRepo;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepo userRepo;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Override
  public Optional<UserDto> addUser(UserDto userDto) {
    String password = userDto.password();

    if (isEmpty(password)) {
      throw new UserApiException("Password can't empty", HttpStatus.BAD_REQUEST);
    }

    var hashedPassword = passwordEncoder.encode(password);

    var udto = new UserDto(userDto.id(), userDto.email(), userDto.username(), hashedPassword);

    User savedUser = userRepo.save(userMapper.convertToUserEntity(udto));

    return Optional.of(userMapper.convertToUserDto(savedUser));
  }

  @Override
  public UserDto getUserById(Integer userId) {
    Optional<User> user = userRepo.findById(userId);
    return user.map(userMapper::convertToUserDto)
        .orElseThrow(() -> new UserApiException("User not found", HttpStatus.NOT_FOUND));
  }

  @Override
  public Optional<UserDto> getUserByEmail(String email) {
    return userRepo.findByEmail(email)
        .map(userMapper::convertToUserDto);
  }

  @Override
  public List<UserDto> getUsers() {
    return userRepo.findAll().stream()
        .map(userMapper::convertToUserDto)
        .toList();
  }

  @Override
  public User authenticate(String username, String password) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));

    return userRepo.findByUsername(username).orElseThrow();
  }
}
