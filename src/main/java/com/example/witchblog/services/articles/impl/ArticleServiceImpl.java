package com.example.witchblog.services.articles.impl;

import com.example.witchblog.dto.request.ArticleRequest;
import com.example.witchblog.dto.response.ArticleResponse;
import com.example.witchblog.entity.articles.Article;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.exceptions.ArticleNotFoundException;
import com.example.witchblog.repositories.articles.ArticleRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.articles.ArticleService;
import com.example.witchblog.services.articles.mapper.ArticleMapper;
import com.example.witchblog.services.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UserService userService;

    @Override
    public ArticleResponse save(ArticleRequest articleRequest, UserDetailsImpl currentUser) {
        final User user = userService.findCurrentUser(currentUser);
        Article article = Article.builder()
                .content(articleRequest.getContent())
                .title(articleRequest.getTitle())
                .createdBy(user)
                .createdDate(LocalDateTime.now())
                .build();
        return articleMapper.map(articleRepository.save(article));
    }

    @Override
    public ArticleResponse update(Long articleId, ArticleRequest articleUpdateContent, UserDetailsImpl currentUser) {
        final User modifiedBy = userService.findCurrentUser(currentUser);
        Article article = findById(articleId);

        article.setModifiedBy(modifiedBy);
        article.setModifiedDate(LocalDateTime.now());
        article.setContent(articleUpdateContent.getContent());
        article.setTitle(articleUpdateContent.getTitle());

        return articleMapper.map(articleRepository.save(article));
    }

    @Override
    public ArticleResponse delete(Long articleId) {
        final Article article = findById(articleId);
        articleRepository.delete(article);
        return articleMapper.map(article);
    }

    @Override
    public Page<ArticleResponse> findAll(Pageable pageable) {
        return new PageImpl<>(articleRepository
                .findAll(pageable)
                .stream()
                .map(articleMapper::map)
                .collect(Collectors.toList())
        );
    }

    @Override
    public ArticleResponse findResponseById(Long id) {
        return articleMapper.map(findById(id));
    }

    @Override
    public Article findById(Long id) {
        return articleRepository
                .findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }
}
