package com.cooksys.twitter_api.dtos;

import com.cooksys.twitter_api.entities.Profile;
import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;

    private ProfileDto profile;

    private Timestamp joined;

}
