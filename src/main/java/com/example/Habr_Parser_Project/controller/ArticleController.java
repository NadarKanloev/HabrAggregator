package com.example.Habr_Parser_Project.controller;

import com.example.Habr_Parser_Project.model.article.Articles;
import com.example.Habr_Parser_Project.repository.JDBCRepository;
import com.example.Habr_Parser_Project.repository.articlesRepo.ArticlesRepository;
import com.example.Habr_Parser_Project.service.BookmarkService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ArticleController {
    private ArticlesRepository articlesRepository;
    private BookmarkService bookmarkService;
    JDBCRepository jdbcRepository;
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
    @PostMapping("/bookmark/{articleId}")
    public ResponseEntity<String> bookmark(@PathVariable long articleId) {
        if(!articlesRepository.existsByarticleId(articleId)){
            return new ResponseEntity<>("Записи не существует", HttpStatus.BAD_REQUEST);
        }
        try {
            bookmarkService.bookMark(articleId);
            return new ResponseEntity<>("Успешно", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Произошла ошибка: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/bookmark/{userId}")
    public ResponseEntity<List<Articles>> getBookmarkedArticlesByUserId(@PathVariable long userId){
        List<Long> ArticlesID = jdbcRepository.getArticleIdsByUserId(userId);
        List<Articles> articles = new ArrayList<>();
        for(long articleid : ArticlesID){
            articles.add(articlesRepository.findByarticleIdAndBodyIsNotNull(articleid));
        }
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
}
