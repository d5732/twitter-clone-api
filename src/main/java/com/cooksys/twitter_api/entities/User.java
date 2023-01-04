package com.cooksys.twitter_api.entities;

import java.util.List;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
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
