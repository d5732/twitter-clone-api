package com.cooksys.twitter_api.entities;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
  	@GeneratedValue
	private Long id;
	
    @ManyToOne
    @JoinColumn(name = "id")
	private User author;

	private Timestamp posted;
	
	private boolean deleted;
	
    private Optional<String> content;

    @OneToMany(mappedBy = "tweet")
    private Optional<Tweet> inReplyTo;

    @OneToMany(mappedBy = "tweet")
    private Optional<Tweet> repostOf;

    private List<Hashtag> hashtagList;

}
