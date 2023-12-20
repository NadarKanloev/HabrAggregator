package com.example.Habr_Parser_Project.service;

import com.example.Habr_Parser_Project.model.article.Articles;
import com.example.Habr_Parser_Project.repository.JDBCRepository;
import com.example.Habr_Parser_Project.repository.articlesRepo.ArticlesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FavoritesService {
    JDBCRepository jdbcRepository;
    UserService userService;
    ArticlesRepository articlesRepository;
    public Map<String, Integer> getNames(){
        long userID = userService.getID();
        List<Long> articlesID = jdbcRepository.getArticleIdsByUserId(userID);
        List<Articles> articles = new ArrayList<>();
        for(long articleID : articlesID){
            articles.add(articlesRepository.findByarticleIdAndBodyIsNotNull(articleID));
        }
        Map<String, Integer> names = new LinkedHashMap<>();
        for(Articles article : articles){
            if(names.containsKey(article.getAuthor())){
                names.put(article.getAuthor(), names.get(article.getAuthor()) + 1);
            }else {
                names.put(article.getAuthor(), 0);
            }
        }
        return names;
    }
    public List<String> getFavoriteAuthors() {
        Map<String, Integer> names = getNames();
        List<String> favoriteAuthors = new ArrayList<>();

        while (!names.isEmpty() && names.size() > names.size() / 2) {
            String favoriteAuthor = names.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (favoriteAuthor != null) {
                favoriteAuthors.add(favoriteAuthor);
                names.remove(favoriteAuthor);
            }
        }
        return favoriteAuthors;
    }
    public static <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    public Map<String, Integer> getHubs(){
        long userID = userService.getID();
        List<Long> articlesID = jdbcRepository.getArticleIdsByUserId(userID);
        List<Articles> hubs = new ArrayList<>();
        for(long articleID : articlesID){
            hubs.add(articlesRepository.findByarticleIdAndBodyIsNotNull(articleID));
        }
        Map<String, Integer> hubsNames = new LinkedHashMap<>();
        for(Articles hub : hubs){
            if(hubsNames.containsKey(hub.getHubId())){
                hubsNames.put(hub.getHubId(), hubsNames.get(hub.getHubId()) + 1);
            }else {
                hubsNames.put(hub.getHubId(), 0);
            }
        }
        return hubsNames;
    }
    public List<String> getFavoriteHubs() {
        Map<String, Integer> hubNames = getHubs();
        List<String> favoriteHubs = new ArrayList<>();

        while (!hubNames.isEmpty() && hubNames.size() > hubNames.size() / 2) {
            String favoriteAuthor = hubNames.entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);

            if (favoriteAuthor != null) {
                favoriteHubs.add(favoriteAuthor);
                hubNames.remove(favoriteAuthor);
            }
        }
        return favoriteHubs;
    }
}
