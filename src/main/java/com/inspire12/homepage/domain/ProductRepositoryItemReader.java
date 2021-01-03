package com.inspire12.homepage.domain;

import com.inspire12.homepage.domain.model.Article;
import com.inspire12.homepage.domain.repository.ArticleRepository;
import org.springframework.batch.item.database.AbstractPagingItemReader;

import java.time.LocalDate;

public class ProductRepositoryItemReader extends AbstractPagingItemReader<Article> {
    private final ArticleRepository articleRepository;
    private final LocalDate txDate;

    public ProductRepositoryItemReader(ArticleRepository articleRepository, LocalDate txDate, int pageSize) {
        this.articleRepository = articleRepository;
        this.txDate = txDate;
        setPageSize(pageSize);
    }

    @Override
    protected void doReadPage() {

    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
