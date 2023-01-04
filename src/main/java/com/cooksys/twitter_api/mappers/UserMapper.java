package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto entityToDto(User user);

	User DtoToEntity(UserRequestDto userRequestDto);
}
