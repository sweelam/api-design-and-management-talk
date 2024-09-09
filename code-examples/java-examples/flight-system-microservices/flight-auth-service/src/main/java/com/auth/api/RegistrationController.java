package com.auth.api;

import com.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegistrationController {
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetails;

  @GetMapping("/login")
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
