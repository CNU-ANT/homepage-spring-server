package com.inspire12.homepage.controller.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspire12.homepage.message.CommentResponse;
import com.inspire12.homepage.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    @PutMapping("/comments")
    public ResponseEntity saveComment(@RequestBody ObjectNode requestBody) {

        commentService.saveByRequest(requestBody);
        ObjectNode responseBody = objectMapper.createObjectNode();
        List<CommentResponse> comments = commentService.getComments(requestBody.get("article_id").asLong());
        responseBody.putPOJO("comments", comments);
        return new ResponseEntity<ObjectNode>(responseBody, HttpStatus.OK);
    }

    // user id
    @PostMapping("/comments")
    public ResponseEntity modifyComment(@RequestBody ObjectNode requestBody) {
        System.out.println(requestBody.get("message"));
//        commentService.saveComment(comment);
        return ResponseEntity.ok().build();
    }

}

