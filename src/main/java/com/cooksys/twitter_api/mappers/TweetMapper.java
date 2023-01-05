package com.cooksys.twitter_api.mappers;

import java.util.List;

import com.cooksys.twitter_api.dtos.TweetRequestDto;
import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.*;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet dtoToEntity(TweetRequestDto tweetRequestDto);

}
