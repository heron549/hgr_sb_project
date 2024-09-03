package com.hgr.project.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hgr.project.entity.Article;
import com.hgr.project.entity.Comment;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.repository.CommentRepository;

//TODO #1-3-2 : 서비스 적용
@Service
public class CommentService {
	// TODO #1-3-3 : 리뷰레파지토리 DI(의존성 주입) -> @Autowired, Setter, constructor
	@Autowired
	private CommentRepository commentRepository;
	
	/**
	 * 리뷰 등록 메소드
	 * @param article    : 리뷰의 부모에 해당되는(N:1) 부모 객체
	 * @param content : 리뷰 내용
	 */
	// TODO #1-3-4 : 리뷰 데이터를 리뷰 엔티티(하나 생성되야함)에 세팅해서, save() 사용하여 입력	
	public void create(Article article, String content) {
		// TODO #1-3-5 : 리뷰 엔티티 생성 및 데이터 세팅
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateDate(LocalDateTime.now());	// 서버측의 시간
		comment.setArticle(article);
		// TODO #1-3-6 : 리뷰 엔티티 저장 -> insert SQL 작동
		this.commentRepository.save(comment);
	}

	public Comment selectOneComment(Integer id) {
		// 실습 2분 => getOnearticle와 패턴 동일함
		Optional<Comment> oComment = this.commentRepository.findById(id);
		if(oComment.isPresent()) {
			return oComment.get();
		}
		// 커스텀 예외 상황
		throw new DataNotFoundException("article not found");
	}

	public void delete(Comment Comment) {
		this.commentRepository.delete(Comment);
	}

	public void modify(Comment Comment) {
		this.commentRepository.save(Comment);
	}
}
