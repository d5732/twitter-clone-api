package com.cooksys.twitter_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.service.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

	private final ValidateService validateService;
	
//	/**
//	 * 
//	 * GET validate/username/available/@{username} #45
//	 * 
//	 * Checks whether or not a given username is available
//	 * 
//	 */
	@GetMapping("/username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username) {
		
		return validateService.usernameAvailable(username);
	}	
	
	/**
	 * 
	 * GET validate/username/exist/@(username)
	 * 
	 * Checks whether or not a given username exists.
	 * 
	 * Response 'boolean'
	 */
	@GetMapping("/username/exists/@{username}")
	public boolean usernameExists(@PathVariable String username) {
		
		return validateService.usernameExists(username);
	}	
	
	/**
	 * 
	 * GET validate/tag/exists/{label} endpoint
	 * 
	 * Checks whether or not a given hashtag exists.
	 * 
	 * Response 'boolean'
	 */
	@GetMapping("/tag/exists/{label}")
	public boolean hashtagExists(@PathVariable String label) {
		
		return validateService.hashtagExists(label);
	}	
	

	
}
