package com.example.witchblog.controllers.articles;

import com.example.witchblog.dto.request.ArticleRequest;
import com.example.witchblog.dto.response.ArticleResponse;
import com.example.witchblog.security.userDetails.CurrentUser;
import com.example.witchblog.security.userDetails.UserDetailsImpl;
import com.example.witchblog.services.articles.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> findById(@PathVariable Long id){
        final ArticleResponse articleResponse = articleService.findResponseById(id);
        return ResponseEntity.ok(articleResponse);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleResponse>> findAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                                         @RequestParam(defaultValue = "createdDate") String sortBy){

        final Page<ArticleResponse> articlesResponse =
                articleService.findAll(PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending()));
        return ResponseEntity.ok(articlesResponse);
    }

    @PostMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> saveArticle(@CurrentUser UserDetailsImpl currentUser,
                                                       @RequestBody @Valid ArticleRequest articleRequest){
        final ArticleResponse savedArticle = articleService.save(articleRequest, currentUser);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/articles/{id}")
                .buildAndExpand(savedArticle.getId()).toUri();
        return ResponseEntity.created(location).body(savedArticle);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable Long id,
                                                       @CurrentUser UserDetailsImpl currentUser,
                                                       @RequestBody @Valid ArticleRequest articleRequest){
        final ArticleResponse updatedArticle = articleService.update(id, articleRequest, currentUser);
        return ResponseEntity.ok(updatedArticle);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> deleteArticle(@PathVariable Long id){
        final ArticleResponse deletedArticle = articleService.delete(id);
        return ResponseEntity.ok(deletedArticle);
    }
}
