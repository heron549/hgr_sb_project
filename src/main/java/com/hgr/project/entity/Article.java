package com.hgr.project.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Article {

	// json 변경시 무시, 단 id에서는 미사용 -> 리뷰를 가져올려면 필요
	// @JsonIgnore
	@Id
	// 오라클 11g 미지원 , 18이상은 지원
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// 오라클 11g에 맞게 수정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator1")
	@SequenceGenerator(name = "sequence_generator1", sequenceName = "sequence_name1", allocationSize = 1)
	private Integer id;

	@Column(length = 128)
	private String subject;

	@Column(length = 1024)
	private String content;

	private LocalDateTime createDate;
	
	@JsonIgnore
	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;

	// @Transient => 클레스 속성으로만 존재
	
	public Article(String subject, String content, LocalDateTime createDate) {
		super();
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
	}
	public Article(Integer id, String subject, String content, LocalDateTime createDate) {
		super();
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.createDate = createDate;
	}

}
