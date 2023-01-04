package com.cooksys.twitter_api.mappers;

import org.mapstruct.Mapper;

import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;

@Mapper(componentModel = "spring")
public interface CredentialsMapper {

	CredentialsResponseDto entityToDto(Credentials credentials);

	Credentials DtoToEntity(CredentialsRequestDto credentialsRequestDto);
}
