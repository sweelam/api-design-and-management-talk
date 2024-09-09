package com.auth.api;

import com.auth.dto.UserDto;
import com.auth.service.JwtService;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import static com.auth.api.ApiResponseUtils.toResponse;

@RestController
@RequestMapping("/api/public/users")
@RequiredArgsConstructor
public class RegistrationController {
  private final UserService userService;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetails;

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
