package com.cooksys.twitter_api.service;

import com.cooksys.twitter_api.dtos.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface TweetService {

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    ResponseEntity<TweetRequestDto> createTweetReply(TweetRequestDto tweetRequestDto);

    ResponseEntity<TweetRequestDto> replyToTweet(Long ID, TweetRequestDto tweetRequestDto);

    ResponseEntity<TweetRequestDto> repostTweet(Long ID, TweetRequestDto tweetRequestDto);

    ResponseEntity<TweetResponseDto> getTags(Long id);

    List<TweetResponseDto> getReposts(Long id);

    List<UserResponseDto> getMentions(Long id);

    ////////////////////////////////////////////////////////////////////////////////////////////////

    void likeTweet(Long id, UserRequestDto userRequestDto);

    List<TweetResponseDto> getTweets();

    TweetResponseDto deleteTweet(Long id, TweetRequestDto tweetRequestDto);

    List<UserResponseDto> getLikes(Long id);

    TweetResponseDto getTweet(Long id);

    ContextDto getContext(Long id);

    List<TweetResponseDto> getReplies(Long id);

}
