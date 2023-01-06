package com.cooksys.twitter_api.entities;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
