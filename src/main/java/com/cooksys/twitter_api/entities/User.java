package com.cooksys.twitter_api.entities;

import java.security.Timestamp;

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
	
	private String username;
	
	private String password;
	
	private Timestamp joined;
	
	private boolean deleted;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String phone;
	
	//@OneToMany(mappedBy = "follower_id")
	//private Followers followers;
	
	//@OneToMany(mappedBy = "user_likes")
	//private UserLikes userLikes;
	
	//@ManyToOne(mappedBy = "user_mentions")
	//private UserMentions userMentions;
	
	//@OneToMany(mappedBy = "tweet")
	//private Tweet tweet;
	
	
	
	
}
