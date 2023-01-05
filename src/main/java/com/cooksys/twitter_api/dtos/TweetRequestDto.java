package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class TweetRequestDto {

	private String content;

	List<CredentialsDto> credentials;

}
