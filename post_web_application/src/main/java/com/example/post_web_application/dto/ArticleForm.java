package com.example.post_web_application.dto;

import com.example.post_web_application.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
    private String author;

    public Article toEntity(){
        return new Article(id, title, content, author, null);
    }
}
