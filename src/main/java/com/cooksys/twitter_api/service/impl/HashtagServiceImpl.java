package com.cooksys.twitter_api.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.HashtagMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.service.HashtagService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
	/**
	 * TODO: add fields for repositories and mappers
	 */
	private final HashtagRepository hashtagRepository;
	private final HashtagMapper hashtagMapper;
	private final TweetRepository tweetRepository;
	private final TweetMapper tweetMapper;




	private static class SortReverseChronological implements Comparator<Tweet> {

		public int compare(Tweet a, Tweet b) {

			return (int) (b.getPosted().getTime() - a.getPosted().getTime());
		}
	}

	/**
	 *
	 * GET tags endpoint #62
	 * 
	 * Retrieves all hashtags tracked by the database.
	 * 
	 * Response ['Hashtag']
	 */
	@Override
	public List<HashtagDto> getAllHashtags() {

		return hashtagMapper.entitiesToDtos(hashtagRepository.findAll());
	}

	/**
	 * GET tags/{label} endpoint #61
	 * 
	 * Retrieves all (non-deleted) tweets tagged with the given hashtag label. The
	 * tweets should appear in reverse-chronological order. If no hashtag with the
	 * given label exists, an error should be sent in lieu of a response.
	 * 
	 * A tweet is considered "tagged" by a hashtag if the tweet has content and the
	 * hashtag's label appears in that content following a #
	 * 
	 * Response ['Tweet']
	 */
	@Override
	public List<TweetResponseDto> getTweetsWithLabel(String label) {
		Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabelAndDeletedFalse(label);
		
		if (optionalHashtag.isEmpty()) {
			throw new NotFoundException("There are no tweets with the hashtag: " + label);
		}	
		
		ArrayList<Tweet> nondeletedTweets = new ArrayList<>();
		
		for (Tweet tweet : tweetRepository.findAllByDeletedFalse()) {
				if (optionalHashtag.get().getTweetList().contains(tweet))
				nondeletedTweets.add(tweet);
		}									
		nondeletedTweets.sort(new SortReverseChronological());
		
		return tweetMapper.entitiesToDtos(nondeletedTweets);
	}

}
