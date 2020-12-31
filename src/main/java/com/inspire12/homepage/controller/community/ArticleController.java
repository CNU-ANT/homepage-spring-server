package com.inspire12.homepage.controller.community;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.inspire12.homepage.message.ArticleResponse;
import com.inspire12.homepage.domain.model.Article;
import com.inspire12.homepage.message.ArticleRequest;
import com.inspire12.homepage.service.board.ArticleService;
import com.inspire12.homepage.service.board.FileMetaService;
import com.inspire12.homepage.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final UserService userService;
    private final FileMetaService fileMetaService;

    @GetMapping("/boards")
    public List<ArticleResponse> showArticle(@RequestParam(value = "size", required = true) int size) {
        return articleService.showArticleMsgs(size);
    }

    @GetMapping("/articles/{id}")
    public ArticleResponse showArticleList(@PathVariable Long id) {

        return articleService.getArticleMsgById(id);
    }

    @PostMapping("/articles")
    public boolean updateArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        Article article = articleService.updateArticle(articleRequest);
        ArrayNode files = articleRequest.getFiles();
        fileMetaService.saveFileMetas(files, article);
        return true;
    }

    @PutMapping("/articles")
    @Transactional
    public boolean insertArticle(@RequestBody ObjectNode articleRequest) {
        Article article = Article.createFromRequest(articleRequest);
        articleService.saveArticle(article);
        ArrayNode files = (ArrayNode) articleRequest.get("files");
        fileMetaService.saveFileMetas(files, article);
        return true;
    }

    @PutMapping("/articles/replies")
    public boolean insertArticleReply(@RequestBody ObjectNode requestBody) {
        Article article = Article.createFromRequest(requestBody);
        long parentId = requestBody.get("parent_id").asLong();
        articleService.saveArticleReply(parentId, article);
        return true;
    }

    @DeleteMapping("/articles")
    public boolean deleteArticle(@RequestParam Long id, @RequestHeader HttpHeaders headers) {
        // httpheader;
        return articleService.deleteArticle(id);
    }
}
