package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.Optional;

@NoArgsConstructor
@Data
public class TweetResponseDto {

	private Long id;

	private UserResponseDto author;

	private Timestamp posted;

	private Optional<String> content;

	private Optional<TweetResponseDto> inReplyTo;

	private Optional<TweetResponseDto> repostOf;

}
