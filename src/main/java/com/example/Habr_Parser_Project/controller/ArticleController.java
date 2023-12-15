package com.example.Habr_Parser_Project.controller;

import com.example.Habr_Parser_Project.model.article.Articles;
import com.example.Habr_Parser_Project.repository.articlesRepo.ArticlesRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ArticleController {
    private ArticlesRepository articlesRepository;
    @GetMapping("/articles")
    public ResponseEntity<List<Articles>> get10Articles(){
        List<Articles> articles = articlesRepository.findFirst10ByBodyIsNotNullOrderByPubDatetimeDesc();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    @GetMapping("/articles/{id}")
    public ResponseEntity<Articles> getArticleById(@PathVariable Long id) {
        Articles article = articlesRepository.findByarticleIdAndBodyIsNotNull(id);
        return new ResponseEntity<>(article, HttpStatus.OK);
    }
    @GetMapping("/authors/{author}")
    public ResponseEntity<List<Articles>> getArticlesByAuthor(@PathVariable String author){
        List<Articles> articles = articlesRepository.findByauthor(author);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
    @GetMapping("/articles/hubs/{hub_id}")
    public ResponseEntity<List<Articles>> getArticlesByHub(@PathVariable String hub_id){
        List<Articles> articles = articlesRepository.findByhubIdAndBodyIsNotNull(hub_id);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
