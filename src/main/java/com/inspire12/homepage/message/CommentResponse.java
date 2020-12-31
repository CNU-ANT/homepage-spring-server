package com.inspire12.homepage.message;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inspire12.homepage.domain.model.Comment;
import com.inspire12.homepage.domain.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponse {

    int id;

    @JsonProperty("article_id")
    Long articleId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User author;

    int grpno;
    int grpord;
    int depth;

    @JsonProperty("content")
    String content;

    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("updated_at")
    LocalDateTime updatedAt;

    int like;

    public static CommentResponse create(Comment comment){
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setArticleId(comment.getArticleId());
        commentResponse.setAuthor(comment.getUser());
        commentResponse.setContent(comment.getContent());
        commentResponse.setGrpno(comment.getGrpno());
        commentResponse.setGrpord(comment.getGrpord());
        commentResponse.setDepth(comment.getDepth());
        commentResponse.setLike(comment.getLike());
        commentResponse.setCreatedAt(comment.getCreatedAt());
        commentResponse.setUpdatedAt(comment.getUpdatedAt());
        return commentResponse;
    }

    public static CommentResponse createCommentMsg(Comment comment, User user){
        CommentResponse commentResponse = CommentResponse.create(comment);
        commentResponse.setAuthor(user);
        return commentResponse;
    }
}
