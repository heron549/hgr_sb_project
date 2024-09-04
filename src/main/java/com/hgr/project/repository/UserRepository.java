package com.hgr.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hgr.project.entity.SnsUser;

public interface UserRepository extends JpaRepository<SnsUser, Long> {
	Optional<SnsUser> findByUsername(String username);

}
