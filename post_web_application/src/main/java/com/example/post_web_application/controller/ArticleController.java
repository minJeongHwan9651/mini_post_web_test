package com.example.post_web_application.controller;

import com.example.post_web_application.dto.ArticleForm;
import com.example.post_web_application.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.post_web_application.repository.ArticleRepository;

@Controller
public class ArticleController {
    @Autowired
    public ArticleRepository articleRepository;

    @GetMapping("/articles")
    public String articles(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "articles/index";
    }

    @GetMapping("/articles/new")
    public String newArticle() {
        return "articles/newArticle";
    }

    @PostMapping("/articles/new")
    public String newArticle(ArticleForm form) {
        Article article = form.toEntity();
        articleRepository.save(article);
        return "redirect:/articles";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/show";
    }

    @GetMapping("/articles/{id}/edit")
    public String editArticle(@PathVariable Long id, Model model) {
        Article articleEntity = articleRepository.findById(id).orElse(null);
        model.addAttribute("article", articleEntity);
        return "articles/editArticle";
    }

    @PostMapping("/articles/update")
    public String updateArticle(ArticleForm form) {
        Article articleEntity = form.toEntity();
        articleRepository.findById(articleEntity.getId()).ifPresent(targetEntity->{
            targetEntity.patch(articleEntity);
            articleRepository.save(targetEntity);
        }); // JPARepository 문제 해결하기.

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes rttr) {
        Article target = articleRepository.findById(id).orElse(null);
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("message", "Article deleted");
        }
        return "redirect:/articles";
        // index.mustache 파일에서 헤더 부분에 삭제 메시지를 출력하게 수정해야 함.
    }

}
