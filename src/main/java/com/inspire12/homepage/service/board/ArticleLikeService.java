package com.inspire12.homepage.service.board;

import com.inspire12.homepage.domain.model.ArticleLike;
import com.inspire12.homepage.domain.model.ArticleLikePk;
import com.inspire12.homepage.domain.repository.ArticleLikeRepository;
import com.inspire12.homepage.domain.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public boolean incArticleLike(Long postId, String username){
        ArticleLike articleLike = new ArticleLike(postId, username);
        if (articleLikeRepository.existsById(new ArticleLikePk(articleLike.getPostId(), articleLike.getUsername()))){
            return false;
        }
        articleLikeRepository.save(articleLike);
        articleRepository.increaseLikes(postId);
        return true;
    }

    public boolean decArticleLike(Long postId, String username){
        if (articleLikeRepository.existsById(new ArticleLikePk(postId, username))){
            articleLikeRepository.delete(new ArticleLike(postId, username));
            articleRepository.decreaseLikes(postId);
            return true;
        }
        return false;
    }
}
