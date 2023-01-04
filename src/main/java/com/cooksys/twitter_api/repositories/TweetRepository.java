package com.cooksys.twitter_api.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.twitter_api.entities.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Long>{


	Optional<Tweet> findByIdAndDeletedFalse(Long id);
	
	List<Tweet> findAllByDeletedFalse();
	
}
