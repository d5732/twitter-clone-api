package com.cooksys.twitter_api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import com.cooksys.twitter_api.dtos.TweetRequestDto;

import com.cooksys.twitter_api.dtos.TweetResponseDto;


public interface TweetService {
	
	
	  List<TweetResponseDto> getTweets();		// get All Tweets
	  
	  ResponseEntity<TweetResponseDto> postTweets(@PathVariable TweetResponseDto tweetResponseDto);

	  ResponseEntity<TweetResponseDto> getTweet(Long id);

	  TweetResponseDto deleteTweet(Long id);	
	  
	  TweetResponseDto likeTweet(Long id);

	  ResponseEntity<TweetRequestDto> createTweetReply(@PathVariable TweetRequestDto tweetRequestDto);

	  ResponseEntity<TweetRequestDto> replyToTweet(Long ID, @PathVariable TweetRequestDto tweetRequestDto);
	  
	  	  
	  ResponseEntity<TweetRequestDto> repostTweet(Long ID, @PathVariable TweetRequestDto tweetRequestDto);

	  
	  ResponseEntity<TweetResponseDto> getTags(Long id);
	  
	  
	  ResponseEntity<TweetResponseDto> getLikes(Long id);

	  
	  ResponseEntity<TweetResponseDto> getContext(Long ID, TweetResponseDto tweetResponseDto);

	  ResponseEntity<TweetResponseDto> getReplies(Long ID, TweetResponseDto tweetResponseDto);

	  ResponseEntity<TweetResponseDto> getReposts(Long id);
	  
	  
	  ResponseEntity<TweetResponseDto> getMentions(Long id);

}
