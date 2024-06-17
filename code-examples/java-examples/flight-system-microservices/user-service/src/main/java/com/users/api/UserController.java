package com.users.api;

import com.users.dto.UserDto;
import com.users.exceptions.UserApiException;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public DeferredResult<List<UserDto>> getAllCustomers() {
        List<UserDto> users = userService.getUsers();
        return ApiResponseUtils.toResponse(users);
    }

    @GetMapping("/{email}")
    public DeferredResult<UserDto> getCustomerByEmail(@PathVariable String email) {
        return ApiResponseUtils.toResponse(userService.getUserByEmail(email));
    }
}
