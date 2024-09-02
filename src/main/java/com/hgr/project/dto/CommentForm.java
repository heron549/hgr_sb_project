package com.hgr.project.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
	
	@NotEmpty(message="내용을 입력해주세요.")
	@Size(max=100)
	private String content;

}
