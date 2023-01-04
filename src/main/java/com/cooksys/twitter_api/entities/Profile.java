package com.cooksys.twitter_api.entities;

import jakarta.persistence.Embeddable;

import lombok.NoArgsConstructor;
import lombok.Data;

@Embeddable
@NoArgsConstructor
@Data
public class Profile {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
