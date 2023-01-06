package com.cooksys.twitter_api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.twitter_api.dtos.HashtagDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.service.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class HashtagController {
	
	private final HashtagService hashtagService;
	
	@GetMapping
	public List<HashtagDto> getAllHashtags(){
		
		return hashtagService.getAllHashtags();
	}
	
	@GetMapping("/{label}")
	List<TweetResponseDto> getTweetsWithLabel(Long id, String label){
		
		return hashtagService.getTweetsWithLabel(label);
	}
	
}
