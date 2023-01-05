package com.cooksys.twitter_api.mappers;

import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.entities.Profile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    ProfileDto entityToDto(Profile profile);

    Profile dtoToEntity(ProfileDto profileDto);

}
