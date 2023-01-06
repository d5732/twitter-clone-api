package com.cooksys.twitter_api.service;

import java.util.List;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;

public interface HashtagService {
	/**
	 * TODO: add fields for repositories and mappers
	 */

	/**
	 *
	 * GET tags endpoint #62
	 * 
	 * Retrieves all hashtags tracked by the database.
	 * 
	 * Response ['Hashtag']
	 */
	List<HashtagDto> getAllHashtags();

	/**
	 * GET tags/{label} endpoint #61
	 * 
	 * Retrieves all (non-deleted) tweets tagged with the given hashtag label. The tweets should appear in reverse-chronological order. If no hashtag with the given label exists, an error should be sent in lieu of a response.
	 * 
	 * A tweet is considered "tagged" by a hashtag if the tweet has content and the hashtag's label appears in that content following a #
	 * 
	 * Response ['Tweet']
	 */
	List<TweetResponseDto> getTweetsWithLabel(String label);

}
