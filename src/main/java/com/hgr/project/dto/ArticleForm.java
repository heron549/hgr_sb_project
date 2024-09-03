package com.hgr.project.dto;

import java.time.LocalDateTime;

import com.hgr.project.entity.Article;

import groovy.transform.builder.Builder;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleForm {
	
	@Transient
	private Integer id;
	
	@NotEmpty(message="제목을 입력해주세요")
	@Size(max=100)
	private String subject;
	
	
	@NotEmpty(message="내용을 입력해주세요")
	@Size(max=100)
	private String content;
	
	
	//private List<Review> reviewList;

		@Builder
		public ArticleForm(String subject,String content) {
			super();
			this.subject = subject;
			this.content = content;
		}
		// id는 자동 생성, 생성시간은 현재시간, 엔티티 생성
		public Article toEntity() {
			return new Article(subject, content, LocalDateTime.now());
		}
		public Article toEntityModify() {
			return new Article(id, subject, content, LocalDateTime.now());
		}
		
	}
	


