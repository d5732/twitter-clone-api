package com.cooksys.twitter_api.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Credentials {

    private String username;

    private String password;

}
