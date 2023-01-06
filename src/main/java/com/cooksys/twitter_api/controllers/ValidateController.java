package com.cooksys.twitter_api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.service.ValidateService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

//	private final ValidateService validateService;
//	
//	@GetMapping("/tag/exists/{label}")
//	public Boolean hashtagExists(String label) {
//		
//		return validateService.hashtagExists(label);
//	}
}
