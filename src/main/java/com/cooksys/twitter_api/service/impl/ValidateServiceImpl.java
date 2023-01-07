package com.cooksys.twitter_api.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.ValidateService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
	
	private HashtagRepository hashtagRepository;
	private UserRepository userRepository;

		
	/**
	 * 
	 * GET validate/username/available/@{username} #45
	 * 
	 * Checks whether or not a given username is available
	 * 
	 */
	@Override
	public boolean usernameAvailable(String username) {
		
		Optional<User> user = userRepository.findByCredentialsUsername(username);
        
		if (user.isEmpty()) {
            return true;
        }
		
        return false;
	}

	/**
	 * 
	 * GET validate/username/exist/@(username)
	 * 
	 * Checks whether or not a given username exists.
	 * 
	 * Response 'boolean'
	 */
	@Override
	public boolean usernameExists(String username) {
        
		Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);

        if (user.isEmpty()) {
            return false;
        }

        return true;
	}
	

	/**
	 * 
	 * GET validate/tag/exists/{label} endpoint
	 * 
	 * Checks whether or not a given hashtag exists.
	 * 
	 * Response 'boolean'
	 */
	@Override
	public boolean hashtagExists(String label) {

        Optional<Hashtag> hashtag = hashtagRepository.findByLabel(label);

        if (hashtag == null) {
            return false;
        }

        return true;
	}

}
