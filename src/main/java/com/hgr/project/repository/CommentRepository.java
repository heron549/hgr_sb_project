package com.hgr.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hgr.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	// JpaRepository는 기본적인 CRUD 작업을 수행하는 메소드들을 이미 제공 => {} 없음
	
	// 댓글 개수를 가져오는 커스텀 메서드
	@Query("SELECT COUNT(c) FROM Comment c")
    long countComments(); 

}
