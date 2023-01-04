package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

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
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

}
