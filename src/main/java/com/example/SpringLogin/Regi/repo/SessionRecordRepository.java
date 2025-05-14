package com.example.SpringLogin.Regi.repo;

import com.example.SpringLogin.Regi.entity.SessionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRecordRepository extends JpaRepository<SessionRecord, Long> {


    List<SessionRecord> findByEmail(String email);
}
