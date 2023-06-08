package com.example.witchblog.exceptions;

public class ArticleNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ArticleNotFoundException(Long articleId){super("Article with id: "+articleId+" not found.");}
}
