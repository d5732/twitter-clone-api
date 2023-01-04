package com.cooksys.twitter_api.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    @ManyToOne
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

}
