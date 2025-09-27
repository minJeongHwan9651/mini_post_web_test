package com.example.post_web_application.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@Entity
@NoArgsConstructor
@ToString
@Getter
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @Column
    String title;
    @Column
    String content;
    @Column
    String author;
    @Column
    LocalDateTime createdAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    public void patch(Article article) {
        if(article.title != null){
            this.title = article.title;
        }
        if(article.content != null){
            this.content = article.content;
        }
        if(article.author != null){
            this.author = article.author;
        }
    }
}
