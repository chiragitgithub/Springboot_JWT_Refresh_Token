package com.example.SpringLogin.Regi.controller;

import com.example.SpringLogin.Regi.jwt.JwtUtil;
import com.example.SpringLogin.Regi.service.AuthService;
import com.example.SpringLogin.Regi.dto.JwtResponse;
import com.example.SpringLogin.Regi.dto.LoginRequest;
import com.example.SpringLogin.Regi.dto.RefreshTokenRequest;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request, HttpSession session) {
        JwtResponse response = authService.authenticate(request, session);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(@Valid @RequestBody RefreshTokenRequest request, HttpSession session) {
        return ResponseEntity.ok(authService.refreshToken(request, session));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String accessToken = authHeader.substring(7);
        String email = jwtUtil.extractUsername(accessToken);

        authService.logout(email, accessToken);
        return ResponseEntity.ok("Logged out successfully");
    }
}
