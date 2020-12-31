package com.inspire12.homepage.controller.community;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspire12.homepage.interceptor.MethodAllowLevel;
import com.inspire12.homepage.service.board.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecommendController {
    private final RecommendService recommendService;

    @MethodAllowLevel(allow = MethodAllowLevel.UserRole.USER)
    @PostMapping("recommend")
    public ResponseEntity<ObjectNode> recommendArticle(@RequestParam Integer articleId, @RequestParam String username) {
        boolean isSuccess = recommendService.recommendToggleArticle(articleId, username);
        return ResponseEntity.of(null);
    }
}
