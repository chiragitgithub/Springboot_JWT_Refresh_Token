package com.example.SpringLogin.Regi.service;


import java.time.LocalDateTime;

public interface SessionRecordService {
    void createSession(String sessionId, String email, LocalDateTime createdAt, LocalDateTime expiresAt);
    void deactivateSessionsByEmail(String email);
}
