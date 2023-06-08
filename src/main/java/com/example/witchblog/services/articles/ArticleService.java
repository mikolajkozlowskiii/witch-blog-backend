package com.example.witchblog.services.articles;

import com.example.witchblog.dto.request.ArticleRequest;
import com.example.witchblog.dto.response.ArticleResponse;
import com.example.witchblog.entity.articles.Article;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    ArticleResponse save(ArticleRequest articleRequest, UserDetailsImpl currentUser);
    ArticleResponse update(Long articleId, ArticleRequest articleUpdateContent, UserDetailsImpl currentUser);
    ArticleResponse delete(Long articleId);
    Page<ArticleResponse> findAll(Pageable pageable);
    ArticleResponse findResponseById(Long id);
    Article findById(Long id);
}
