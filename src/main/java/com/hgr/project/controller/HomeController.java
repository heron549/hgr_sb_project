package com.hgr.project.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hgr.project.entity.Article;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.service.ArticleService;
import com.hgr.project.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	public final ArticleService articleService;
	public final CommentService commentService;

	@GetMapping("/")
	public String home(Model model) {
		
		// 게시글 수 표시
		List<Article> articles = this.articleService.getAllArticle();
		model.addAttribute("articleCount", articles.size());

		// 댓글 수 표시 (카운트 쿼리 사용)
		long commentCount = this.commentService.getCommentCount();
		model.addAttribute("commentCount", commentCount);


		// 최신 게시글 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}

		return "home";
	}

	@GetMapping("/location")
	public String location(Model model) {
		// 최신 게시글 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "location";
	}

	@GetMapping("/videos")
	public String videos(Model model) {
		// 최신 게시글 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "lecture_videos";
	}
	
	@GetMapping("/game")
	public String game(Model model) {
		// 최신 게시글 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "game";
	}
	
	
	

}
