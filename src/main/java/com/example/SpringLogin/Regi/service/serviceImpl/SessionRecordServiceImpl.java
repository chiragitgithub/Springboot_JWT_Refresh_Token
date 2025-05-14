package com.example.SpringLogin.Regi.service.serviceImpl;


import com.example.SpringLogin.Regi.entity.SessionRecord;
import com.example.SpringLogin.Regi.repo.SessionRecordRepository;
import com.example.SpringLogin.Regi.service.SessionRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionRecordServiceImpl implements SessionRecordService {

    private final SessionRecordRepository sessionRecordRepository;

    @Override
    public void createSession(String sessionId, String email, LocalDateTime createdAt, LocalDateTime expiresAt) {
        SessionRecord session = new SessionRecord();
        session.setSessionId(sessionId);
        session.setEmail(email);
        session.setCreatedAt(createdAt);
        session.setExpiresAt(expiresAt);
        session.setActive(true);
        sessionRecordRepository.save(session);
    }

    @Override
    public void deactivateSessionsByEmail(String email) {
        List<SessionRecord> sessions = sessionRecordRepository.findByEmail(email);
        for (SessionRecord session : sessions) {
            session.setActive(false);
        }
        sessionRecordRepository.saveAll(sessions);
    }
}
