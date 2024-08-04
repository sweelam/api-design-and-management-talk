package com.users.service.impl;

import com.users.dto.UserDto;
import com.users.entity.User;
import com.users.exceptions.UserApiException;
import com.users.mappers.UserMapper;
import com.users.repo.UserRepo;
import com.users.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.private-key}")
    private String privateKey;

    @Override
    public Optional<UserDto>        addUser(UserDto userDto) {
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
        return user.map(userMapper::convertToUserDto).orElseThrow(() ->
                new UserApiException("User not found", HttpStatus.NOT_FOUND));
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
    public String generateJwt(String username, String password) {
                return userRepo.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .map(user -> Jwts.builder()
                        .subject(   user.getUsername())
                        .claim("roles", user.getAuthorities())
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                        .signWith(getSignInKey())
                        .compact()
                )
                .orElseThrow(() -> new UserApiException("user not found"));
    }

    private Key getSignInKey() {
        byte[] keyBytes = privateKey.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
