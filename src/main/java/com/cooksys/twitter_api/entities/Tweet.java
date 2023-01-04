package com.cooksys.twitter_api.entities;

import java.security.Timestamp;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
public class Tweet {
	
	@Id
  	@GeneratedValue
	private Long id;	
	
    @OneToOne(mappedBy = "user_table")
   // @JoinColumn(name = "id")
	private String author;
	
	private Timestamp posted;
	
	private boolean deleted;
	
    private Optional<String> content;
    
 //   private Credentials credentials;

    //fk - 1 tweet can have many reposts
    @OneToMany(mappedBy = "tweet")
    @JoinColumn(name = "repostOf")
    private Optional<Long> repostOf;		

    // fk - 1 tweet can have many replies
    @OneToMany(mappedBy = "tweet")
    @JoinColumn(name = "inReplyTo")
     private Optional<Long> inReplyTo;		

}
