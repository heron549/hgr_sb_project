package com.hgr.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hgr.project.entity.Article;
import com.hgr.project.entity.Comment;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.repository.CommentRepository;


@Service
public class CommentService {
	
	// 왜 @Autowired로 의존성주입(DI)하지??
	@Autowired
	private CommentRepository commentRepository;
	
	
	public void create(Article article, String content) {
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateDate(LocalDateTime.now());	// 서버측의 시간
		comment.setArticle(article);
		// 리뷰 엔티티 저장 -> insert SQL 작동
		this.commentRepository.save(comment);
	}

	public Comment selectOneComment(Integer id) {
		
		
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

	
	// 댓글 수 표시
	public long getCommentCount() {
		
		return this.commentRepository.countComments();
	}

	
	

}
