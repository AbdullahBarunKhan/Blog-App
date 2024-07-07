package com.projects.blogapp.articles;

import com.projects.blogapp.users.UsersEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity(name = "articles")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false )
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String body;

    @NonNull
    @Column(unique = true)
    private String slug;

    @Nullable
    private String subtitle;

    @CreatedDate
    private Date createdAt;

    @ManyToOne
    @JoinColumn(nullable = false, name = "authorId" )
    private UsersEntity author;


}
