package com.projects.blogapp.articles;

import com.projects.blogapp.users.UsersEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articles")
public class ArticlesController {

        @GetMapping("")
        String getArticles(){
            return "get All articles";
        }

        @GetMapping("/{id}")
    String getArticlesById(@PathVariable("id") String id){
            return "get articles by id : " + id;
        }

        @PostMapping()
    String createArticles(@AuthenticationPrincipal UsersEntity usersEntity){
            return "create article called by " + usersEntity.getUsername();
        }
}
