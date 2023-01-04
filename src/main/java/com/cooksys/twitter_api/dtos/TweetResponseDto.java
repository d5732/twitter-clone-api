package com.cooksys.twitter_api.dtos;

import java.security.Timestamp;
import java.util.List;
import java.util.Optional;

import com.cooksys.twitter_api.entities.*;

public class TweetResponseDto {
	
	private Long id;
	
	private UserResponseDto author;
	
	private Timestamp posted;

    private Optional<String> content;
    
    private Optional<TweetResponseDto> inReplyTo;

    private Optional<TweetResponseDto> repostOf;



}
