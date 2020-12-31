package com.inspire12.homepage.service.board;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspire12.homepage.message.CommentResponse;
import com.inspire12.homepage.domain.model.Comment;
import com.inspire12.homepage.domain.repository.CommentRepository;
import com.inspire12.homepage.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> getComments(Long articleId) {
        List<Comment> comments = commentRepository.selectCommentByArticleOrder(articleId);
        return convertToCommentMsgs(comments);
    }

    public void saveByRequest(ObjectNode request) {
        String userId = request.get("username").asText();
        Long articleId = request.get("article_id").asLong();
        String content = request.get("content").asText();
        Comment comment = Comment.create(userId, articleId, content);
        try{
            commentRepository.insertByRequest(articleId, userId, content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveReplyByRequest(ObjectNode request) {
        String userId = request.get("username").asText();
        Long articleId = request.get("article_id").asLong();
        String content = request.get("content").asText();
        int parentCommentId = request.get("parent_id").asInt();

//        Comment childComment = createComment(userId, articleId, content);
        try{
            Comment parentComment = commentRepository.findById(parentCommentId).get();
            commentRepository.updateReplyOrder(parentComment.getGrpno(), parentComment.getGrpord());
            commentRepository.insertByRequest(articleId, userId, content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<CommentResponse> convertToCommentMsgs(List<Comment> comments) {
        List<CommentResponse> commentResponses = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponse commentResponse = CommentResponse.createCommentMsg(comment, userRepository.findById(comment.getUsername()).get());
            commentResponses.add(commentResponse);
        }
        return commentResponses;
    }
}
