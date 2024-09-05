package com.hgr.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hgr.project.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // 아이디로 부터 리플레시토큰 엔티티 획득
	Optional<RefreshToken> findByUserId(Long userId);
    // 리플레시 토큰으로 부터 리플레시토큰 엔티티 획득
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
