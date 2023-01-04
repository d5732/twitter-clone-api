package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.ProfileRequestDto;
import com.cooksys.twitter_api.dtos.ProfileResponseDto;
import com.cooksys.twitter_api.entities.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileResponseDto entityToDto(Profile profile);

    Profile DtoToEntity(ProfileRequestDto profileRequestDto);

}
