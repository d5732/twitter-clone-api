package com.cooksys.twitter_api.service;

public interface ValidateService {
	
//	/**
//	 * 
//	 * GET validate/username/available/@{username} #45
//	 * 
//	 * Checks whether or not a given username is available
//	 * 
//	 */
	boolean usernameAvailable(String username);

	/**
	 * 
	 * GET validate/username/exist/@(username)
	 * 
	 * Checks whether or not a given username exists.
	 * 
	 * Response 'boolean'
	 */	
	boolean usernameExists(String username);

	
	/**
	 * 
	 * GET validate/tag/exists/{label} endpoint
	 * 
	 * Checks whether or not a given hashtag exists.
	 * 
	 * Response 'boolean'
	 */
	boolean hashtagExists(String label);

	


}
