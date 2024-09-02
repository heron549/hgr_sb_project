package com.hgr.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Comment {

	@Id
	// 오라클 11g 미지원 , 18이상은 지원
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	// 오라클 11g에 맞게 수정
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator1")
    @SequenceGenerator(name = "sequence_generator1", sequenceName = "sequence_name1", allocationSize = 1)
	private Integer id;

	@Column(length = 512)
	private String content;

	private LocalDateTime createDate;

	@ManyToOne
	private Article article;

}
