package com.example.SpringLogin.Regi.service.serviceImpl;

import com.example.SpringLogin.Regi.entity.RefreshToken;
import com.example.SpringLogin.Regi.repo.RefreshTokenRepository;
import com.example.SpringLogin.Regi.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    private final Long refreshTokenDurationMs;

    public RefreshTokenServiceImpl(
            RefreshTokenRepository repository,
            @Value("${app.refresh-token.expiry-ms}") Long refreshTokenDurationMs)  {
        this.repository = repository;
        this.refreshTokenDurationMs = refreshTokenDurationMs;
    }

    @Override
    @Transactional
    public RefreshToken createRefreshToken(String email) {
        repository.deleteByEmail(email);
        RefreshToken token = new RefreshToken();
        token.setEmail(email);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        return repository.save(token);
    }

    @Override
    public boolean validateRefreshToken(String token) {
        return repository.findByToken(token)
                .filter(t -> t.getExpiryDate().isAfter(Instant.now()))
                .isPresent();
    }

    @Override
    @Transactional
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }
}
