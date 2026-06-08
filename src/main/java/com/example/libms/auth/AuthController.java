package com.example.libms.auth;

import com.example.libms.auth.dto.LoginRequest;
import com.example.libms.auth.dto.RefreshRequest;
import com.example.libms.auth.dto.TokenResponse;
import com.example.libms.borrower.IBorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final IBorrowerService borrowerService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        // Throws AuthenticationException if invalid
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        UserDetails user = (UserDetails) auth.getPrincipal();

        return ResponseEntity.ok(new TokenResponse(
                tokenService.generateAccessToken(user),
                tokenService.generateRefreshToken(user)
        ));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        // Validate refresh token and extract email
        String email = tokenService.extractSubject(request.getRefreshToken());

        // Load the user
        UserDetails user = borrowerService.loadUserByUsername(email);

        // Issue new tokens
        String newAccessToken  = tokenService.generateAccessToken(user);
        String newRefreshToken = tokenService.generateRefreshToken(user);

        return ResponseEntity.ok(new TokenResponse(newAccessToken, newRefreshToken));
    }
}