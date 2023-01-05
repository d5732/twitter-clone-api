package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.User;

@Mapper(componentModel = "spring", uses = { ProfileMapper.class, CredentialsMapper.class })
public interface UserMapper {

	//TODO: Fix the error produced here
//	@Mapping(target = "username", source = "credentials.username")
	UserResponseDto entityToDto(User user);

	User DtoToEntity(UserRequestDto userRequestDto);
}
