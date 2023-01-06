package com.cooksys.twitter_api.service;

import com.cooksys.twitter_api.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface TweetService {

    // To do
	  
	  ResponseEntity<TweetRequestDto> createTweetReply(@PathVariable TweetRequestDto tweetRequestDto);

	  
	  ResponseEntity<TweetResponseDto> getTags(Long id);
	  
	  ResponseEntity<TweetResponseDto> getReposts(Long id);
	  
	  
	  ResponseEntity<TweetResponseDto> getMentions(Long id);
	  

	  ////////////////////////////////////////////////////////////////////////////////////////////////
	  
	  
	  // Active
		   
	  TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto);


	  TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

	  TweetResponseDto repostTweet(Long ID, TweetRequestDto tweetRequestDto);
	  
	  void likeTweet(Long id, UserRequestDto userRequestDto);
	  
	  List<TweetResponseDto> getTweets();
		  
	  TweetResponseDto deleteTweet(Long id, TweetRequestDto tweetRequestDto);	

	  List<UserResponseDto> getLikes(Long id);

	  TweetResponseDto getTweet(Long id);
	  
	  ContextDto getContext(Long id);
	  
	  List<TweetResponseDto> getReplies(Long id);

}
