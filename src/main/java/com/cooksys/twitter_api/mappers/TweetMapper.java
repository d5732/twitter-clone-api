package com.cooksys.twitter_api.mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;

import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.*;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TweetMapper {


//    TweetResponseDto entityToDto(Tweet entity);
//
//    Tweet Dto_To_Entity(TweetResponseDto entity);
//
//
//    Tweet request_DTO_To_Entity(TweetResponseDto qDTO);
//
//    ///// added /////
//
//
//    ContextDto entityToContextDto(Tweet tweet);
//
//    List<TweetResponseDto> entitiesToDtos(ArrayList<Tweet> feed);
//
//    void entitiesToContextDto();

}


