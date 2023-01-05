package com.cooksys.twitter_api.mappers;

import java.util.List;
import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.*;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TweetMapper {

	TweetResponseDto entityToDto(Tweet entity);

	List<TweetResponseDto> entitiesToDtos(List<Tweet> entities);

	Tweet request_DTO_To_Entity(TweetResponseDto qDTO);

}
