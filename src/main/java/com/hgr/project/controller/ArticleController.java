package com.hgr.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hgr.project.dto.ArticleForm;
import com.hgr.project.dto.CommentForm;
import com.hgr.project.entity.Article;
import com.hgr.project.entity.Comment;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.service.ArticleService;
import com.hgr.project.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping("/article")
//@RequiredArgsConstructor
@Controller
public class ArticleController {

//	public final ArticleService articleService;
//	public final CommentService commentService;
	@Autowired
	public ArticleService articleService;
	@Autowired
	public CommentService commentService;

//	// 게시글 리스트
//	@GetMapping("/list")
//	public String list(Model model) {
//
//		// 게시물 수 표시
//		List<Article> articles = this.articleService.getAllArticle();
//		model.addAttribute("articleCount", articles.size());
//
//		// 댓글 수 표시 (카운트 쿼리 사용)
//		long commentCount = this.commentService.getCommentCount();
//		model.addAttribute("commentCount", commentCount);
//
//		model.addAttribute("articles", articles);
//		model.addAttribute("dumy_year", 2024);
//
//
//
//		// 최신 게시물 가져오기
//		try {
//			Article latestArticle = articleService.getLatestArticle();
//			model.addAttribute("latestArticle", latestArticle);
//		} catch (DataNotFoundException e) {
//			model.addAttribute("message", e.getMessage());
//		}
//
//		return "article_list";
//	}

	// ~/bbs/list?page=1 이런 방식으로 호출
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

		// 1. 특정 페이지(인자)에 해당되는 페이징 데이터 획득
		Page<Article> paging = this.articleService.getList(page);
		// 2. html에 전달
		model.addAttribute("paging", paging);

		// 게시글 수 표시
//		List<Article> articles = this.articleService.getAllArticle();
//		model.addAttribute("articleCount", articles.size());
//		model.addAttribute("articles", articles);

		// 게시글 수 표시 (카운트 쿼리 사용)
		long articleCount = this.articleService.getArticleCount();
		model.addAttribute("articleCount", articleCount);

		// 댓글 수 표시 (카운트 쿼리 사용)
		long commentCount = this.commentService.getCommentCount();
		model.addAttribute("commentCount", commentCount);

		model.addAttribute("dumy_year", 2024);

		// 최신 게시물 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}

		return "article_list";
	}

	// 검색어 획득, 검색 작업 실제 진행(서비스-레퍼지토리 처리), 타임리프 전달내용(검색어, 페이징번호)
	@GetMapping("/list2")
	public String list2(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		// 1. 특정 페이지(인자)에 해당되는 페이징 데이터 획득
		Page<Article> paging = this.articleService.getList2(page, keyword);
		// 2. html에 전달
		model.addAttribute("keyword", keyword);
		model.addAttribute("paging", paging);
		// 검색된 게시글 개수 가져오기
		model.addAttribute("articleCount", paging.getTotalElements());

		List<Article> articles = paging.getContent();
		// 댓글 개수 계산
		long totalComments = articles.stream().flatMap(article -> article.getCommentList().stream()).count();
		model.addAttribute("commentCount", totalComments);

		// 최신 게시물 가져오기
		try {
			Article latestArticle = articleService.getLatestArticle();
			model.addAttribute("latestArticle", latestArticle);
		} catch (DataNotFoundException e) {
			model.addAttribute("message", e.getMessage());
		}

		return "article_list";
	}

	// 게시글 상세보기
	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id, CommentForm commentForm) {

		Article a = this.articleService.getOneArticle(id);
		model.addAttribute("article", a);

		return "article_detail";
	}

	// 게시글 생성
	@GetMapping("/create")
	public String create(Model model, ArticleForm articleForm) {
		model.addAttribute("method", "post");
		return "article_form";
	}

	@PostMapping("/create")
	public String create(@Valid ArticleForm articleForm, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("method", "post");
			return "article_form";
		}
		this.articleService.create(articleForm.getSubject(), articleForm.getContent());

		return "redirect:/article/list";
	}

	// 게시글 수정
	@GetMapping("/modify/{id}")
	public String modify(Model model, ArticleForm articleForm, @PathVariable("id") Integer id) {
		Article article = this.articleService.getOneArticle(id);
		articleForm.setSubject(article.getSubject());
		articleForm.setContent(article.getContent());
		model.addAttribute("method", "put");
		return "article_form";
	}

	@PutMapping("/modify/{id}")
	public String modify(@Valid ArticleForm articleForm, BindingResult bindingResult, @PathVariable("id") Integer id,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("method", "put");

			return "article_form";
		}
		Article article = this.articleService.getOneArticle(id);
		article.setSubject(articleForm.getSubject());
		article.setContent(articleForm.getContent());
		this.articleService.modify(article);
		return "redirect:/article/detail/" + id;
	}

	// 게시글 삭제
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		Article article = this.articleService.getOneArticle(id);
		this.articleService.delete(article);
		return "redirect:/article/list";
	}

}
