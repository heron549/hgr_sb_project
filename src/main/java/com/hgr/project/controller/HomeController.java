package com.hgr.project.controller;

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

	@GetMapping("/")
	public String home(Model model) {
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}

		return "home";
	}
	
	
	@GetMapping("location")
	public String location(Model model) {
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}
		return "location";
	}
	

}
