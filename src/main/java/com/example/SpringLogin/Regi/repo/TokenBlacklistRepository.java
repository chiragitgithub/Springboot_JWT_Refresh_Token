package com.example.SpringLogin.Regi.repo;

import com.example.SpringLogin.Regi.entity.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {
     boolean existsByToken(String token);
}
