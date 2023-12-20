package com.example.Habr_Parser_Project.controller;

import com.example.Habr_Parser_Project.service.FavoritesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class FavoritesController {
    FavoritesService favoritesService;
    @GetMapping("/favAuthors")
    public ResponseEntity<List<String>> getFavAuthors(){
        List<String> authors = favoritesService.getFavoriteAuthors();
        if(authors.size() > 0){
            return new ResponseEntity<>(authors, HttpStatus.OK);
        }else {
            authors.add("Авторов нет");
            return new ResponseEntity<>(authors, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/favHubs")
    public ResponseEntity<List<String>> getFavHubs(){
        List<String> hubs = favoritesService.getFavoriteHubs();
        if(hubs.size() > 0){
            return new ResponseEntity<>(hubs, HttpStatus.OK);
        }else {
            hubs.add("Авторов нет");
            return new ResponseEntity<>(hubs, HttpStatus.BAD_REQUEST);
        }
    }
}
