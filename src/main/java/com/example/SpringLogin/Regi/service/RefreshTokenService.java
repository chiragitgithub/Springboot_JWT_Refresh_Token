package com.example.SpringLogin.Regi.service;

import com.example.SpringLogin.Regi.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(String email);

    boolean validateRefreshToken(String token);

    void deleteByEmail(String email);

    Optional<RefreshToken> findByToken(String token);
}
