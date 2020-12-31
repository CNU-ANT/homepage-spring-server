package com.inspire12.homepage.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.inspire12.homepage.domain.model.*;
import com.inspire12.homepage.util.ArticleUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ArticleResponse {

    Long id;
    int no;
    int depth;
    String category;
    String subject;
    String content;
    String url;

    @JsonProperty("author")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User author;

    @JsonProperty("comments")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    List<CommentResponse> comments;

    @JsonProperty("files")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    List<FileMeta> files = new ArrayList<>();

    @JsonProperty("created_at")
    LocalDateTime createdAt;
    @JsonProperty("updated_at")
    LocalDateTime updatedAt;
    @JsonProperty("board_id")
    int boardId;
    List<String> tags;
    Integer hit;

    List<Recommend> like;

    @JsonProperty("is_my_like")
    Boolean isMyLike;

    @JsonProperty("is_deleted")
    Boolean isDeleted;

    public static ArticleResponse create(Article article, boolean isMyLike){
        ArticleResponse articleResponse = create(article);
        articleResponse.setIsMyLike(isMyLike);
        return articleResponse;
    }

    public static ArticleResponse create(Article article){
        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setId(article.getId());
        articleResponse.setNo(article.getGrpno());
        articleResponse.setCategory(ArticleUtil.getArticleCategory(article.getBoardId()));
        articleResponse.setDepth(article.getDepth());
        articleResponse.setSubject(article.getSubject());
        articleResponse.setContent(article.getContent());
        articleResponse.setCreatedAt((article.getCreatedAt()));
        articleResponse.setUpdatedAt((article.getUpdatedAt()));
        articleResponse.setBoardId(article.getBoardId());
        if (article.getTags() != null && !article.getTags().equals(""))
            articleResponse.setTags(Arrays.asList(article.getTags().split(",")));
        articleResponse.setAuthor(article.getUser());

        articleResponse.setFiles(article.getFileMetas());

        articleResponse.setHit(article.getHit());
        articleResponse.setLike(article.getLikes());
        articleResponse.setIsDeleted(article.getIsDeleted());
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : article.getComments()){
            commentResponses.add(CommentResponse.create(comment));
        }

        articleResponse.setComments(commentResponses);
        return articleResponse;
    }

    public static ArticleResponse createWithUser(Article article, User user) {
        ArticleResponse articleResponse = ArticleResponse.create(article);
        articleResponse.setAuthor(user);
        return articleResponse;
    }

    public static ArticleResponse createWithComments(Article article, User user, List<CommentResponse> commentResponses) {

        ArticleResponse articleResponse = createWithUser(article, user);
        articleResponse.setComments(commentResponses);
        return articleResponse;
    }
}
