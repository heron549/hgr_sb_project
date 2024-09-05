package com.hgr.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hgr.project.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

	// 최신 게시글 1개 가져오기
	Article findFirstByOrderByCreateDateDesc();

	// 페이징(Page, Pageable) 형태의 리뷰 데이터를 가져오는 메소드 선언
	// findAll() => 모든 데이터를 다가져온다
	// 아래처럼 선언하면 => 특정 위치에서 특정 개수만큼 페이징하여 필요한것만 가져온다
	Page<Article> findAll(Pageable pageable);
	// 페이징, 정렬, 키워드검색(복잡한 쿼리가 작동할수 있다)
	// findAll(샘플쿼리, 페이징)
	Page<Article> findAll(Specification<Article> sf, Pageable pageable);
	
	// 게시글 개수를 가져오는 커스텀 메서드
	@Query("SELECT COUNT(a) FROM Article a")
    long countArticles();
}


