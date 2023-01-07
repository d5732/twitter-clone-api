package com.cooksys.twitter_api.service;

import com.cooksys.twitter_api.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface TweetService {

    // To do
	  
	  ResponseEntity<TweetRequestDto> createTweetReply(TweetRequestDto tweetRequestDto);

	  
	  ResponseEntity<TweetResponseDto> getTags(Long id);
	  
	  List<TweetResponseDto> getReposts(Long id);
	  
	  
	  List<UserResponseDto> getMentions(Long id);
	  

	  ////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  
	  // Active
		   
	  TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);


	  TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	  TweetResponseDto repostTweet(Long ID, TweetRequestDto tweetRequestDto);
	  
	  void likeTweet(Long id, CredentialsDto credentialsDto);
	  
	  List<TweetResponseDto> getTweets();
		  
	  TweetResponseDto deleteTweet(Long id, TweetRequestDto tweetRequestDto);	

	  List<UserResponseDto> getLikes(Long id);

	  TweetResponseDto getTweet(Long id);
	  
	  ContextDto getContext(Long id);
	  
	  List<TweetResponseDto> getReplies(Long id);

}
