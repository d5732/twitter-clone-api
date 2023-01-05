package com.cooksys.twitter_api.dtos;

import com.cooksys.twitter_api.entities.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;
    private Profile profile;
    private Timestamp joined;


}
