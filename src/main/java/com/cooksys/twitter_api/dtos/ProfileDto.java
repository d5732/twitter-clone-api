package com.cooksys.twitter_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Data
public class ProfileDto {

	private Optional<String> firstName;

	private Optional<String> lastName;

	private String email;

	private Optional<String> phone;

}
