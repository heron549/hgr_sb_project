package com.hgr.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hgr.project.dto.ArticleForm;
import com.hgr.project.dto.CommentForm;
import com.hgr.project.entity.Article;
import com.hgr.project.entity.Comment;
import com.hgr.project.service.ArticleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RequestMapping("/article")
@RequiredArgsConstructor
@Controller
public class ArticleController {
	
	public final ArticleService articleService;
	
	
	
	
	@GetMapping("/list")
	public String list(Model model) {
		List<Article> articles = this.articleService.getAllArticle();
		model.addAttribute("articles", articles);
		model.addAttribute("dumy_year", 2024);
		model.addAttribute("articleCount", articles.size()); // 게시물 수 추가
		
		return "article_list";
	}
	
	
	
	
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm 
			) {
		
		Article a = this.articleService.getOneArticle(id);
		model.addAttribute("article", a);	
		
		return "article_detail";
	}
	
	
	
	
	
	
	@GetMapping("/create")
	public String create(Model model, ArticleForm articleForm) {
		model.addAttribute("method", "post");
		return "article_form";
	}
	
	@PostMapping("/create")
	public String create(@Valid ArticleForm articleForm, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "article_form";
		}
		this.articleService.create(articleForm.getSubject(), articleForm.getContent());
		
		return "redirect:/article/list";
	}
	
	
	
	
	@GetMapping("/modify/{id}")
	public String modify(Model model, ArticleForm articleForm, @PathVariable("id") Integer id) {
		Article article = this.articleService.getOneArticle(id);
		articleForm.setSubject( article.getSubject() );
		articleForm.setContent( article.getContent() );
		model.addAttribute("method", "put");
		return "article_form";
	}
	
	@PutMapping("/modify/{id}")
	public String modify(@Valid ArticleForm articleForm, BindingResult bindingResult,
			 			 @PathVariable("id") Integer id) {
		if( bindingResult.hasErrors() ) {
			return "article_form";
		}
		Article article = this.articleService.getOneArticle(id);
		article.setSubject( articleForm.getSubject() );
		article.setContent( articleForm.getContent() );
		this.articleService.modify(article);
		return "redirect:/article/detail/" + id;
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		Article article = this.articleService.getOneArticle(id);
		this.articleService.delete( article );
		return "redirect:/article/list";
	}
	
	

}
