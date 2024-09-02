package com.hgr.project.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {

	@Id
	// 오라클 11g 미지원 , 18이상은 지원
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 오라클 11g에 맞게 수정
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator1")
//	@SequenceGenerator(name = "sequence_generator1", sequenceName = "sequence_name1", allocationSize = 1)
	private Integer id;

	@Column(length = 128)
	private String subject;

	@Column(length = 1024)
	private String content;

	private LocalDateTime createDate;

	@OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
	private List<Comment> commentList;

	// @Transient => 클레스 속성으로만 존재

}
