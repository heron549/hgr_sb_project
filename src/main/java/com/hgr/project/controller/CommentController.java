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


@RequestMapping("/comment")
@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	
	@PostMapping("/create/{id}")
	
	public String create(Model model, @PathVariable("id") Integer id,
			@Valid CommentForm commentForm, BindingResult bindingResult) {
		Article article = this.articleService.getOneArticle(id);
		// 오류 검사 
		if( bindingResult.hasErrors() ) {

			model.addAttribute("article", article);
			return "article_detail";
		}
		
		this.commentService.create(article , commentForm.getContent());
		
		return "redirect:/article/detail/" + id;
	}
	


	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {


		Comment comment = this.commentService.selectOneComment( id );
		this.commentService.delete( comment );


		return "redirect:/article/detail/" + comment.getArticle().getId();
	}

	@GetMapping("/modify/{id}")
	public String modify(CommentForm commentForm, @PathVariable("id") Integer id) {


		Comment comment = this.commentService.selectOneComment(id);


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
