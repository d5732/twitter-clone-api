package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@Data
public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;
    private String label;
    private Timestamp firstUsed;
    private Timestamp lastUsed;
    private boolean deleted;

    @ManyToMany(mappedBy = "hashtagList")
    private List<Tweet> tweetList;

}
