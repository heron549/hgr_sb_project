package com.hgr.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "user_comments") // 엔티티 'Comment'가 'user_comments' 테이블에 매핑됨
@Entity
public class Comment {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_generator2")
    @SequenceGenerator(name = "sequence_generator2", sequenceName = "sequence_name2", allocationSize = 1)
	private Integer id;

	@Column(length = 512)
	private String content;

	private LocalDateTime createDate;

	@ManyToOne
	private Article article;

}
