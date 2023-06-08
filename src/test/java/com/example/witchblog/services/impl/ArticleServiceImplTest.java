package com.example.witchblog.services.impl;

import com.example.witchblog.entity.articles.Article;
import com.example.witchblog.entity.users.User;
import com.example.witchblog.exceptions.ArticleNotFoundException;
import com.example.witchblog.repositories.articles.ArticleRepository;
import com.example.witchblog.repositories.users.UserRepository;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.articles.ArticleService;
import com.example.witchblog.services.articles.impl.ArticleServiceImpl;
import com.example.witchblog.services.articles.mapper.ArticleMapper;
import com.example.witchblog.services.users.RoleService;
import com.example.witchblog.services.users.UserService;
import com.example.witchblog.services.users.impl.UserServiceImpl;
import com.example.witchblog.services.users.mappers.UserMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {
    @InjectMocks
    private ArticleServiceImpl articleService;
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleMapper articleMapper;
    @Mock
    private UserService userService;

    @Test
    public void findById_ArticleExistsInDb_RetunrsArticleResponse(){
        final Long articleId = 1L;
        final Article expectedArticle = Article.builder().id(1L).build();

        when(articleRepository.findById(1L)).thenReturn(Optional.of(expectedArticle));
        final Article actualArticle = articleService.findById(articleId);

        Assertions.assertEquals(expectedArticle, actualArticle);
    }

    @Test
    public void findById_ArticleNotExistsInDb_RetunrsArticleResponse(){
        final Long articleId = 1L;
        final Article expectedArticle = Article.builder().id(1L).build();

        when(articleRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(ArticleNotFoundException.class, () -> articleService.findById(articleId));
    }
}
