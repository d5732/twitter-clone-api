package com.cooksys.twitter_api.entities;

import jakarta.persistence.Embeddable;

import lombok.NoArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {

    private Timestamp joined;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
