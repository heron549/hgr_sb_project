package com.hgr.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hgr.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	// 필요한 메소드(디비에 연동되어서 쿼리 수행) 선언 => {} 없음

}
