package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.entity.TokenBlacklist;
import com.example.SpringLogin.Regi.repo.TokenBlacklistRepository;
import com.example.SpringLogin.Regi.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final TokenBlacklistRepository tokenBlacklistRepository;

    public TokenBlacklistServiceImpl(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    @Override
    public void blacklistToken(String token) {
        if (!tokenBlacklistRepository.existsByToken(token)) {
            TokenBlacklist entry = new TokenBlacklist();
            entry.setToken(token);
            entry.setBlacklistedAt(LocalDateTime.now());
            tokenBlacklistRepository.save(entry);
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklistRepository.existsByToken(token);
    }
}
