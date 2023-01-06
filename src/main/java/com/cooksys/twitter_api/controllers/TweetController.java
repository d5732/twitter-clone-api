package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

	  private final TweetService tweetService;

	   
	    @PostMapping
	    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto) {
			return tweetService.createTweet(tweetRequestDto);
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
	    
	    @PostMapping("/{id}/like")
	    public void likeTweet(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
	    	
	    	tweetService.likeTweet(id, userRequestDto);

	    }

	
	    
	    @GetMapping
	    public List<TweetResponseDto> getTweets() {
			
	    	
	    	return tweetService.getTweets();
	    

	    }
	    
	    @GetMapping("tweets/{id}/replies")
	    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
	    	
	    	return tweetService.getReplies(id);
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
	    public ContextDto getContext(@PathVariable Long id) {		// 
	    	
			return tweetService.getContext(id);

	    }
}
