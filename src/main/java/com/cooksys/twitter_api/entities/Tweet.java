package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Tweet {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    private Timestamp posted;

    private boolean deleted;

    private String content;

    @ManyToOne
    private Tweet inReplyTo;

    @ManyToOne
    private Tweet repostOf;

    @ManyToMany
    private List<Hashtag> hashtagList;

    @ManyToMany(mappedBy = "likesTweetList")
    private List<User> likesUserList;

    @ManyToMany(mappedBy = "mentionsTweetList")
    private List<User> mentionsUserlist;

}
