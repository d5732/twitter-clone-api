package com.cooksys.twitter_api.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table(name="user_table")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private Credentials credentials;

	@Embedded
	private Profile profile;

	private boolean deleted;
	
	@OneToMany(mappedBy = "tweet")
	private Tweet tweet;
	
	@ManyToOne
	@JoinColumn(name = "follower_id")
	private List<User> followers;
	
	@ManyToOne
	@JoinColumn(name = "following_id")
	private List<User>  following;
	
	@ManyToOne
	@JoinColumn(name = "user_likes")
	private List<Tweet> userLikes;

	@ManyToOne
	@JoinColumn(name = "user_mentions")
	private List<Tweet> userMentions;

	
}
