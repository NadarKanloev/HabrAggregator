package com.example.Habr_Parser_Project.repository.articlesRepo;

import com.example.Habr_Parser_Project.model.article.Articles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticlesRepository extends JpaRepository<Articles, Long> {
    Articles findByarticleIdAndBodyIsNotNull(long articleId);
    List<Articles> findFirst10ByBodyIsNotNullOrderByPubDatetimeDesc();
    List<Articles> findByauthor(String author);
    List<Articles> findByhubIdAndBodyIsNotNull(String hub_id);
    boolean existsByarticleId(long articleId);
}