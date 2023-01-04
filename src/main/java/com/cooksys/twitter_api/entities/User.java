package com.cooksys.twitter_api.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany(mappedBy = "user") // child
    private List<User> followers;

    @ManyToMany
    private List<User> following;

    @ManyToMany(mappedBy = "tweet")
    private List<Tweet> likes;

    @ManyToMany
    private List<Tweet> mentions;


}
