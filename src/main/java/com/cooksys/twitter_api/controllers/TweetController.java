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
	    public TweetResponseDto replyToTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
	    	
	    	return tweetService.replyToTweet(id, tweetRequestDto);
	    	
	    }
     

	    @PostMapping("/{id}/repost")
	    public TweetResponseDto repostTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
	    	
	    	return tweetService.repostTweet(id, tweetRequestDto);
	    }


    @GetMapping("/{id}/tags")
    public void getTags() {
    
    }

	    
    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getReposts(@PathVariable Long id) {
        return  tweetService.getReposts(id);
    }


    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentions(@PathVariable Long id) {
        return tweetService.getMentions(id);
    }

    public static class ProfileResponseDto {
    }

    ///////////////////////////////////////////////////////////////////

    @PostMapping("/{id}/like")
    public void likeTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {
        tweetService.likeTweet(id, credentialsDto);
    }


    @GetMapping
    public List<TweetResponseDto> getTweets() {


        return tweetService.getTweets();


    }

    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getReplies(@PathVariable Long id) {
        return tweetService.getReplies(id);
    }

    @GetMapping("/{id}")
    public TweetResponseDto getTweet(@PathVariable Long id) {


        return tweetService.getTweet(id);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody CredentialsDto credentialsDto) {

        return tweetService.deleteTweet(id, credentialsDto);

    }


    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getLikes(@PathVariable Long id) {

        return tweetService.getLikes(id);

    }


    @GetMapping("/{id}/context")
    public ContextDto getContext(@PathVariable Long id) {        //

        return tweetService.getContext(id);

    }
    
}
