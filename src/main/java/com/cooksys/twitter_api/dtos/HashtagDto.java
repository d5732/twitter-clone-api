package com.cooksys.twitter_api.dtos;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class HashtagDto { // as ResponseDto

	private String label;

	private Timestamp firstUsed;

	private Timestamp lastUsed;
}
