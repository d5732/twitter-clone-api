package com.cooksys.twitter_api.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;



@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

	
	  TweetResponseDto entityToDto(Tweet entity);
	  
	  Tweet dtoToEntity(TweetRequestDto tweetRequestDto);


	  List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);
	 	  
	  ///// added /////
	  
	 // List<UserResponseDto> entitiesToUserDtos(List<User> users, HttpStatus ok);
	  
	  List<UserResponseDto> entitiesToUserDtos(List<User> users);

	  
	 // ContextDto entitiesToContextDto(List<Tweet> tweet);
	  
}


