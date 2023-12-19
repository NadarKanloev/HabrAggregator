package com.example.Habr_Parser_Project.service;

import com.example.Habr_Parser_Project.repository.JDBCRepository;
import com.example.Habr_Parser_Project.repository.UserRepository;
import com.example.Habr_Parser_Project.repository.articlesRepo.ArticlesRepository;
import com.example.Habr_Parser_Project.security.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookmarkService {
    UserRepository userRepository;
    JDBCRepository jdbcRepository;
    ArticlesRepository articlesRepository;
    UserService userService;
     public String bookMark(long article_id){
        long user_id = userService.getID();
        if(user_id != -1){
            jdbcRepository.executeSomeQuery(user_id, article_id);
            return "Успешно выполнено";
        }else {
            return "Пользователя с данным ID не существует";
        }
     }

}
