package com.hgr.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hgr.project.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
