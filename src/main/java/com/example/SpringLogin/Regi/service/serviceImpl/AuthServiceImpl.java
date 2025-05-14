package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.entity.RefreshToken;
import com.example.SpringLogin.Regi.jwt.JwtUtil;
import com.example.SpringLogin.Regi.service.AuthService;
import com.example.SpringLogin.Regi.service.RefreshTokenService;
import com.example.SpringLogin.Regi.service.SessionRecordService;
import com.example.SpringLogin.Regi.service.TokenBlacklistService;
import com.example.SpringLogin.Regi.dto.JwtResponse;
import com.example.SpringLogin.Regi.dto.LoginRequest;
import com.example.SpringLogin.Regi.dto.RefreshTokenRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SessionRecordService sessionRecordService;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthServiceImpl(
            RefreshTokenService refreshTokenService,
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            SessionRecordService sessionRecordService,
            TokenBlacklistService tokenBlacklistService
    ) {
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.sessionRecordService = sessionRecordService;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    public JwtResponse authenticate(LoginRequest request, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtUtil.generateToken(request.getEmail());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(request.getEmail());

            sessionRecordService.createSession(
                    session.getId(),
                    request.getEmail(),
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(2)
            );

            return new JwtResponse(token, refreshToken.getToken(), session.getId());
        }

        throw new RuntimeException("Invalid credentials");
    }

    @Override
    public JwtResponse refreshToken(RefreshTokenRequest request, HttpSession session) {
        boolean valid = refreshTokenService.validateRefreshToken(request.getRefreshToken());

        if (!valid) {
            throw new RuntimeException("Refresh token is expired or invalid");
        }

        RefreshToken tokenRecord = refreshTokenService.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Token not found"));

        String newAccessToken = jwtUtil.generateToken(tokenRecord.getEmail());

        return new JwtResponse(newAccessToken, request.getRefreshToken(), session.getId());
    }

    @Override
    public void logout(String email, String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            tokenBlacklistService.blacklistToken(accessToken);
        }

        refreshTokenService.deleteByEmail(email);
        sessionRecordService.deactivateSessionsByEmail(email);
    }
}
