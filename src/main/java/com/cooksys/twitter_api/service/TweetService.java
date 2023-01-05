package com.cooksys.twitter_api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;


public interface TweetService {
	
		  
	  ResponseEntity<TweetResponseDto> postTweets(@PathVariable TweetResponseDto tweetResponseDto);

	  
	  TweetResponseDto likeTweet(Long id);

	  ResponseEntity<TweetRequestDto> createTweetReply(@PathVariable TweetRequestDto tweetRequestDto);

	  ResponseEntity<TweetRequestDto> replyToTweet(Long ID, @PathVariable TweetRequestDto tweetRequestDto);
	  
	  	  
	  ResponseEntity<TweetRequestDto> repostTweet(Long ID, @PathVariable TweetRequestDto tweetRequestDto);

	  
	  ResponseEntity<TweetResponseDto> getTags(Long id);
	  
	  ResponseEntity<TweetResponseDto> getReposts(Long id);
	  
	  
	  ResponseEntity<TweetResponseDto> getMentions(Long id);
	  

	  ////////////////////////////////////////////////////////////////////////////////////////////////
	  

	  List<TweetResponseDto> getTweets();
		  
	  TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto);	

	  List<UserResponseDto> getLikes(@PathVariable Long id);

	  TweetResponseDto getTweet(Long id);
	  
	  ContextDto getContext(@PathVariable Long id);
	  
	  List<TweetResponseDto> getReplies(@PathVariable Long id);



}
