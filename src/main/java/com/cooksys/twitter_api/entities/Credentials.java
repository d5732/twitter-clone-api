package com.cooksys.twitter_api.entities;

import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;
import lombok.Data;

@Embeddable
@NoArgsConstructor
@Data
public class Credentials {

    private String username;

    private String password;

}
