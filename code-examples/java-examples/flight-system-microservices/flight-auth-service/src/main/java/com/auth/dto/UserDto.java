package com.auth.dto;


public record UserDto(
        Integer id, String email,
        String username,
        String password) {}
