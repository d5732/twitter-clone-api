package com.cooksys.twitter_api.dtos;

import java.util.List;

import com.cooksys.twitter_api.entities.Tweet;

public class ContextRequestDto {
	
	private TweetRequestDto target;
	
	
	private List<TweetRequestDto> before;

}
