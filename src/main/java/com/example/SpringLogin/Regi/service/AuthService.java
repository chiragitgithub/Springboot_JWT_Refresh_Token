package com.example.SpringLogin.Regi.service;// AuthService.java
import com.example.SpringLogin.Regi.dto.JwtResponse;
import com.example.SpringLogin.Regi.dto.LoginRequest;
import com.example.SpringLogin.Regi.dto.RefreshTokenRequest;
import jakarta.servlet.http.HttpSession;

public interface AuthService {
    JwtResponse authenticate(LoginRequest request, HttpSession session);
    JwtResponse refreshToken(RefreshTokenRequest request, HttpSession session);

    void logout(String email,String accessToken);
}
