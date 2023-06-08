package com.example.witchblog.dto.request;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ArticleRequest {
    @Length(min = 3, max = 50)
    private String title;
    @Length(min = 10, max = 512)
    private String content;
}
