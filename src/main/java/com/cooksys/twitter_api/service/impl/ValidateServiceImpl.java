package com.cooksys.twitter_api.service.impl;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.service.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private HashtagRepository hashtagRepository;
	
	/**
	 * 
	 * GET validate/tag/exists/{label} endpoint
	 * 
	 * Checks whether or not a given hashtag exists.
	 * 
	 * Response 'boolean'
	 */
//	@Override
//	public Boolean hashtagExists(String label) {
//
//		boolean hashtagToCheck = hashtagRepository.hashtagExists(label);
//
//		return hashtagToCheck;
//	}	

}
