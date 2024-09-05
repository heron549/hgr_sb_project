package com.hgr.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.hgr.project.entity.Article;
import com.hgr.project.exception.DataNotFoundException;
import com.hgr.project.repository.ArticleRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {

	public final ArticleRepository articleRepository;

	// 페이징 계산시 기준이 되는 페이지당 게시물의 개수 => 환경변수도 가능, 10개로 설정
	private final int PAGE_SIZE = 5;

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

	// 게시글 개수를 가져오기
	public long getArticleCount() {
		return this.articleRepository.countArticles();
	}
	
	public Page<Article> getList(int page) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by( sorts ));
		return articleRepository.findAll(pageable);
	}
	
	

	public Page<Article> getList2(int page, String keyword) {
		// 1. 정렬 (요구사항에는 없었지만 추가)
		//    order by a, b, c .... desc(or asc)
		List<Sort.Order> sorts = new ArrayList<>();
		// 검색 결과를 createDate 값 기준으로 내림차순 정렬
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by( sorts ));
		
		// 2. 검색 처리하는 함수 별도 구성 -> 필요시 조인, where 조건을 여러개, 집계 -> 그룹화, having등등 사용
		//    Specification 객체가 복잡한 select문 구성하는 요소
		
		Specification<Article> sp = complexSearch( keyword );
		return articleRepository.findAll(sp, pageable);
	}
	 /**
	  * 
	  * @param keyword : 검색어
	  * @return : 복잡한 쿼리문을 대변하는 Specification<Article> 객체
	  */
	private Specification<Article> complexSearch(String keyword) {
		return new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// 중복제거 => distinct
				query.distinct(true);
				
				// 조인이 필요하면, NaverReviews와 ChatbotSheet간 left 조인 수행, 
				// left 조인 결과 : label 컬럼값이 일치하는 ChatbotSheet 데이터 + NaverReviews 모든 데이터
//				Join<NaverReviews, ChatbotSheet> r2 = root.join("label", JoinType.LEFT);
				
				
				// 해당 컬럼에 검색어가 존재만 하면 다 가져온다
				// like => %검색어, 검색어%, %검색어%
				return criteriaBuilder.like(root.get("content"), "%" + keyword + "%");
				
//				return criteriaBuilder.or(
//				criteriaBuilder.like(root.get("document"), "%" + keyword + "%"),
//				criteriaBuilder.like(r2.get("question"), "%" + keyword + "%"),
//				criteriaBuilder.like(r2.get("answer"), "%" + keyword + "%")
//			);
			}
			
		};
	}
	
	

}
