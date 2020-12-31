package com.inspire12.homepage.service.board;


import com.inspire12.homepage.domain.model.*;
import com.inspire12.homepage.message.ArticleResponse;
import com.inspire12.homepage.message.CommentResponse;
import com.inspire12.homepage.message.ArticleRequest;
import com.inspire12.homepage.domain.repository.ArticleLikeRepository;
import com.inspire12.homepage.domain.repository.ArticleRepository;
import com.inspire12.homepage.domain.repository.CommentRepository;
import com.inspire12.homepage.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final CommentRepository commentRepository;

    public ArticleResponse showArticleMsgById(Long postId) {
        articleRepository.increaseHit(postId);
        return getArticleMsgById(postId);
    }

    public ArticleResponse getArticleMsgById(Long postId) {
        Article article = articleRepository.findById(postId).get();
        Optional<ArticleLike> articleLike = articleLikeRepository.findById(new ArticleLikePk(postId, (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        if(article.getIsDeleted()) {
        }

         return ArticleResponse.create(article, articleLike.isPresent());
    }

    public List<ArticleResponse> showArticleMsgsWithCount(int type, int pageNum, int articleCount) {
        int start = (pageNum - 1) * articleCount;
        List<Article> articles;
        if (type == 0) {
            articles = articleRepository.showArticlesWithArticleCount(start, articleCount);
        } else {
            articles = articleRepository.showArticlesWithArticleByTypeCount(type, start, articleCount);
        }
        return convertArticles(articles);
    }

    public Article updateArticle(ArticleRequest articleRequest) {
        // 데이터 검증
        long id = articleRequest.getId();

        Article article = articleRepository.findById(id).get();
        article.setSubject(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setBoardId(articleRequest.getType());
        articleRepository.save(article);
        return article;
    }

    public List<ArticleResponse> showArticleMsgs(int size) {
        List<Article> articles = articleRepository.selectArticles(PageRequest.of(0, size));
        return convertArticles(articles);
    }

    public boolean saveArticle(Article article) {
        // 데이터 검증
        articleRepository.save(article);
        article.setGrpno(article.getGrpno());
        articleRepository.save(article);
        return true;
    }

    @Transactional
    public boolean saveArticleReply(Long parentId, Article childArticle) {
        Article parentArticle = articleRepository.findById(parentId).get();
        articleRepository.updateReplyOrder(parentArticle.getGrpno(), parentArticle.getGrpord());

        childArticle.setGrpno(parentArticle.getGrpno());
        childArticle.setGrpord(parentArticle.getGrpord() + 1);
        childArticle.setDepth(parentArticle.getDepth() + 1);

        articleRepository.save(childArticle);
        return true;
    }

    public boolean modifyArticle(Article article) {
        articleRepository.save(article);
        return true;
    }

    public boolean deleteArticle(Long articleId) {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (username.equals(articleRepository.getOne(articleId).getUsername())) {
            articleRepository.updateIsDeletedArticle(articleId);
            return true;
        }
        return false;
    }

    private List<ArticleResponse> convertArticles(List<Article> articles) {
        List<ArticleResponse> articleResponses = new ArrayList<>();
        for (Article article : articles) {
            try {
                articleResponses.add(ArticleResponse.create(article));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return articleResponses;
    }

    private List<ArticleResponse> convertArticlesToArticleMsgs(List<Article> articles) {
        List<ArticleResponse> articleResponses = new ArrayList<>();
        for (Article article : articles) {
            try {
                User author = userRepository.findById(article.getUsername()).get();
                List<Comment> comments = commentRepository.selectCommentByArticleOrder(article.getId());
                articleResponses.add(ArticleResponse.createWithComments(article, author, convertToMsg(comments)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return articleResponses;
    }

    private List<CommentResponse> convertToMsg(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            try {
                commentResponses.add(CommentResponse.createCommentMsg(comment, userRepository.getOne(comment.getUsername())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return commentResponses;
    }
}
