package com.cooksys.twitter_api.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.TweetRequestDto;

import com.cooksys.twitter_api.dtos.TweetResponseDto;


import com.cooksys.twitter_api.repositories.TweetRepository;


import com.cooksys.twitter_api.service.TweetService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

	
//	private final TweetRepository tweetRepository;

	@Override
	public List<TweetResponseDto> getTweets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> postTweets(TweetResponseDto tweetResponseDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto deleteTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TweetResponseDto likeTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> createTweetReply(TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> replyToTweet(Long ID, TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> repostTweet(Long ID, TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getTags(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getLikes(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getContext(Long ID, TweetResponseDto tweetResponseDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getReplies(Long ID, TweetResponseDto tweetResponseDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getReposts(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getMentions(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
