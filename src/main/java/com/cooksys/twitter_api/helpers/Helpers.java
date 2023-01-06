package com.cooksys.twitter_api.helpers;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.entities.User;

import java.util.Optional;

public class Helpers {


    public static boolean credentialsAreCorrect(Optional<User> optionalUser, CredentialsDto credentialsDto) {
        return optionalUser.isPresent()
                && isValidCredentialsDto(credentialsDto)
                && optionalUser.get().getCredentials().getUsername().equals(credentialsDto.getUsername())
                && optionalUser.get().getCredentials().getPassword().equals(credentialsDto.getPassword());
    }

    public static boolean isValidProfileDto(ProfileDto profileDto) {
        return profileDto != null
                && profileDto.getEmail() != null
                && !profileDto.getEmail().isEmpty();
    }

    public static boolean isValidCredentialsDto(CredentialsDto credentialsDto) {
        return credentialsDto != null
                && credentialsDto.getUsername() != null
                && credentialsDto.getPassword() != null
                && !credentialsDto.getUsername().isEmpty()
                && !credentialsDto.getPassword().isEmpty();
    }
}
