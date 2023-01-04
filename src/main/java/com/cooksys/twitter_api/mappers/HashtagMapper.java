package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.entities.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
    HashtagResponseDto entityToDto(Hashtag hashtag);

    Hashtag DtoToEntity(HashtagRequestDto hashtagRequestDto);
}
