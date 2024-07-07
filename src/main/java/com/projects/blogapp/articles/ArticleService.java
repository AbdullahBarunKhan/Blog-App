package com.projects.blogapp.articles;

import com.projects.blogapp.articles.DTO.CreateArticleRequest;
import com.projects.blogapp.articles.DTO.UpdateArticleRequest;
import com.projects.blogapp.users.UserRepository;
import com.projects.blogapp.users.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Locale;

public class ArticleService {

    @Autowired
    public ArticlesRepository articlesRepository;
    @Autowired
    public UserRepository userRepository;

    public Iterable<ArticleEntity> getAllArticles(){
        return articlesRepository.findAll();
    }

    public ArticleEntity getArticleBySlug(String slug){
        var article = articlesRepository.findBySlug(slug);

        if(article == null){
            throw new ArticleNotFoundException(slug);
        }

        return article;
    }

    public ArticleEntity createArticle(CreateArticleRequest a, Long authorId){
        var author = userRepository.findById(authorId).orElseThrow(()-> new UserService.UserNotFoundException(authorId));
        return articlesRepository.save(ArticleEntity.builder().title(a.getTitle())
                        .slug(a.getTitle().toLowerCase().replaceAll("\\s+","-"))
                        .subtitle(a.getSubtitle())
                .body(a.getBody()).author(author).build());
    }

    public ArticleEntity updateArticle(UpdateArticleRequest a, Long articleId){
        var article = articlesRepository.findById(articleId).orElseThrow(()-> new UserService.UserNotFoundException(articleId));

        if(a.getTitle() != null ){
            article.setTitle(a.getTitle());
            article.setSlug(a.getTitle().toLowerCase().replaceAll("\\s+","-"));
        }
        if(a.getBody() != null ){
            article.setBody(a.getBody());
        }
        if(a.getSubtitle() != null ){
            article.setSubtitle(a.getSubtitle());
        }

        return article;
    }

    static class ArticleNotFoundException extends IllegalArgumentException{
        public ArticleNotFoundException(String slug){
            super("Article " + slug + " not found.");
        }
    }
}
