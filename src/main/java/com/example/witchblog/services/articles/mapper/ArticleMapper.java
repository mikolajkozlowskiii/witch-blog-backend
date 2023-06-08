package com.example.witchblog.services.articles.mapper;

import com.example.witchblog.dto.response.ArticleResponse;
import com.example.witchblog.entity.articles.Article;
import com.example.witchblog.services.users.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArticleMapper {
    private final UserMapper userMapper;
    public ArticleResponse map(Article article){
        return ArticleResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdDate(article.getCreatedDate())
                .createdBy(userMapper.map(article.getCreatedBy()))
                .modifiedBy(Optional.ofNullable(article.getModifiedBy()).map(userMapper::map).orElse(null))
                .modifiedDate(article.getModifiedDate())
                .build();
    }
}
