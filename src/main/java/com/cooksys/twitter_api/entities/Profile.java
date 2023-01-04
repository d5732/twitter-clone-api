package com.cooksys.twitter_api.entities;

import jakarta.persistence.Embeddable;

import java.security.Timestamp;

@Embeddable
public class Profile {
    private Timestamp joined;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

}
