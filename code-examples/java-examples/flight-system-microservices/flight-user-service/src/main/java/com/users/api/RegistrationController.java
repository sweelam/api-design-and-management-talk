package com.users.api;

import com.users.dto.UserDto;
import com.users.service.JwtService;
import com.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import static com.users.api.ApiResponseUtils.toResponse;

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

  @GetMapping("/internal-login")
  public ResponseEntity<String> login(@RequestHeader String username, @RequestHeader String password) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
    );
    return ResponseEntity.ok(jwtService.generateJwt(userDetails.loadUserByUsername(username)));
  }

  @GetMapping("/pass-hash")
  public ResponseEntity<String> login(@RequestHeader String pass) {
    return ResponseEntity.ok(passwordEncoder.encode(pass));
  }
}
