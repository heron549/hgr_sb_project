package com.hgr.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hgr.project.entity.Article;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

	public final ArticleRepository articleRepository;

	// 페이징 계산시 기준이 되는 페이지당 게시물의 개수 => 환경변수도 가능, 10개로 설정
	private final int PAGE_SIZE = 3;

	public List<Article> getAllArticle() {

		return this.articleRepository.findAll();
	}

	public void create(String subject, String content) {
		Article a = new Article();
		a.setSubject(subject);
		a.setContent(content);
		a.setCreateDate(LocalDateTime.now());
		this.articleRepository.save(a);

	}

	public Article getOneArticle(Integer id) {
		Optional<Article> oArticle = this.articleRepository.findById(id);
		if (oArticle.isPresent()) {
			return oArticle.get();
		}
		throw new DataNotFoundException("article not found");

	}

	public void modify(Article article) {
		this.articleRepository.save(article);

	}

	public void delete(Article article) {
		this.articleRepository.delete(article);

	}

	// 최신 게시글 1개 가져오기
	public Article getLatestArticle() {
		Article article = articleRepository.findFirstByOrderByCreateDateDesc();
		if (article == null) {
			throw new DataNotFoundException("article not found");
		}

		return article;
	}

	public Page<Article> getList(int page) {
		
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		return articleRepository.findAll(pageable);
	}
	
	
	// 게시글 개수를 가져오기
	public long getArticleCount() {
		return this.articleRepository.countArticles();
	}

}
