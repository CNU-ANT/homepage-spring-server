package com.inspire12.homepage.domain;

import com.inspire12.homepage.domain.model.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository

public class ArticleRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;
    public ArticleRepositorySupport(EntityManager em) {
        super(Article.class);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Article> findByName(String name) {
        return new ArrayList();
    }
}
