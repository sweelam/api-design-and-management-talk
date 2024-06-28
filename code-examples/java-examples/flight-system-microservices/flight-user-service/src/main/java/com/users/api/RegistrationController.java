package com.users.api;

import com.users.dto.UserDto;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import static com.users.api.ApiResponseUtils.toResponse;

@RestController
@RequestMapping("/api/public/users")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> registerNewCustomer(@RequestBody UserDto userRequest) {
        return userService.addUser(userRequest)
                .map(res -> new ResponseEntity<>(res, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/{userId}")
    public DeferredResult<UserDto> getUserById(@PathVariable Integer userId) {
        return toResponse(userService.getUserById(userId));
    }
}
