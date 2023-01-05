package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Credentials credentials;

    @Embedded
    private Profile profile;

    private boolean deleted;

    @OneToMany
    private List<Tweet> tweets;

    @ManyToMany(mappedBy = "following")
    private List<User> followers;

    @ManyToMany
    private List<User> following;

    @ManyToMany
    private List<Tweet> likesTweetList;

    @ManyToMany
    private List<Tweet> mentionsTweetList;


}
