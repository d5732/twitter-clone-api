package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.service.TweetService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	  private final TweetService tweetService;

	   
	    @PostMapping
	    public void postTweets() {
	    }

	    @PostMapping("/{id}/like")
	    public void likeTweet() {
	    }


	    @PostMapping("/{id}/reply")
	    public void replyToTweet() {
	    }


	    @PostMapping("/{id}/repost")
	    public void repostTweet() {
	    }


	    @GetMapping("/{id}/tags")
	    public void getTags() {
	    }

	    @GetMapping("/{id}/reposts")
	    public void getReposts() {
	    }


	    @GetMapping("/{id}/mentions")
	    public void getMentions() {
	    }

	    public static class ProfileResponseDto {
	    }
	    
	    ///////////////////////////////////////////////////////////////////
	    
	    @GetMapping
	    public List<TweetResponseDto> getTweets() {
			
	    	
	    	return tweetService.getTweets();
	    

	    }
	    
	    
	    @GetMapping("tweets/{id}/replies")
	    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
	    }
	    
	    @GetMapping("tweets/{id}/")
	    public TweetResponseDto getTweet(@PathVariable Long id) {
	    	
	    	
	    	return tweetService.getTweet(id);
	    }

	    @DeleteMapping("tweets/{id}")
	    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
	    	
			return tweetService.deleteTweet(id, tweetRequestDto);

	    }


	    @GetMapping("tweets/{id}/likes")
	    public List<UserResponseDto> getLikes(@PathVariable Long id) {
	    	
			return tweetService.getLikes(id);

	    }


	    @GetMapping("tweets/{id}/context")
	    public ContextDto getContext(@PathVariable Long id) {
	    	
			return tweetService.getContext(id);

	    }
}
