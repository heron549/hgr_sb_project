package com.hgr.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hgr.project.dto.CommentForm;
import com.hgr.project.entity.Article;
import com.hgr.project.entity.Comment;
import com.hgr.project.service.ArticleService;
import com.hgr.project.service.CommentService;

import jakarta.validation.Valid;

//URL 프리픽스 추가
@RequestMapping("/comment")
//TODO #1-1 : 컨트롤러 기능 부여
@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	/**
	 * 리뷰 생성 함수
	 * @param id
	 * @param content
	 * @return
	 */
	// TODO #1-2 : /comment/create/{id}를 매핑하는 메소드구현
	//@articleMapping("/comment/create/{id}")
	@PostMapping("/create/{id}")
	//public String create(@PathVariable("id") Integer id, 
	//		             @RequestParam(value="content") String content) {
	
	public String create(Model model, @PathVariable("id") Integer id,
			@Valid CommentForm commentForm, BindingResult bindingResult) {
		Article article = this.articleService.getOneArticle(id);
		// 오류 검사 추가
		if( bindingResult.hasErrors() ) {
			// 원래 detatil에 접속하던 방식으로 접근(재현)
			model.addAttribute("article", article);
			return "article_detail";
		}
		// TODO #1-3 : 서비스를 통해서 comment 엔티티 생성하여 디비에 저장 (서비스 생성, 레퍼지토리사용, 엔티티사용)
		// 1-3 구현, 실습 2분
		// 1. commentService 의존성 주입 -> 맴버 변수 자리에서 진행
		// 2. article 엔티티 객체 획득
		// 3. commentService.create(article객체, content) 함수 호출
		// 유효성 폼사용 => content은 폼객체로 부처 추출
		this.commentService.create(article , commentForm.getContent());
		
		// TODO #1-4 : ~/article/detail/{id} 페이지를 요청한다 -> 서버에서 요청 : 리다이렉트
		return "redirect:/article/detail/" + id;
	}
	
	/**
	 * 
	 * @param id : 리뷰 ID
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		// 리뷰 ID => 리뷰 엔티티 획득
		Comment comment = this.commentService.selectOneComment( id );
		this.commentService.delete( comment );
		// article ID를 획득해서 해당 article의 상세페이지로 이동
		return "redirect:/article/detail/" + comment.getArticle().getId();
	}

	@GetMapping("/modify/{id}")
	public String modify(CommentForm commentForm, @PathVariable("id") Integer id) {
		// 실습 1분
		// comment 내용 획득
		Comment comment = this.commentService.selectOneComment(id);
		// commentForm에 내용 설정
		commentForm.setContent(comment.getContent());
		return "comment_form";
	}
	@PostMapping("/modify/{id}")
	public String modify(@Valid CommentForm commentForm, BindingResult bindingResult,
			 			 @PathVariable("id") Integer id) {
		if( bindingResult.hasErrors() ) {
			return "comment_form";
		}
		Comment comment = this.commentService.selectOneComment(id);		
		comment.setContent(commentForm.getContent());
		this.commentService.modify( comment );
		return "redirect:/article/detail/" + comment.getArticle().getId();
	}
}
