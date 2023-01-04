package com.cooksys.twitter_api.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;

public class Hashtag {

    @Id
    @GeneratedValue
    private Long id;
    private String label;
    private Timestamp firstUsed;
    private Timestamp lastUsed
            ;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

}
