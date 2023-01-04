package com.cooksys.twitter_api.entities;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

	//@OneToMany(mappedBy = "follower_id")
	//private Followers followers;
	
	//@OneToMany(mappedBy = "user_likes")
	//private UserLikes userLikes;
	
	//@ManyToOne(mappedBy = "user_mentions")
	//private UserMentions userMentions;
	
	//@OneToMany(mappedBy = "tweet")
	//private Tweet tweet;
	
}
