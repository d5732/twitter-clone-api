package com.cooksys.twitter_api.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.TweetService;
import java.sql.Timestamp;  

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

	
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	


	 
	@Override
	public ResponseEntity<TweetResponseDto> postTweets(TweetResponseDto tweetResponseDto) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TweetResponseDto likeTweet(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> createTweetReply(TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> replyToTweet(Long ID, TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetRequestDto> repostTweet(Long ID, TweetRequestDto tweetRequestDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getTags(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ResponseEntity<TweetResponseDto> getReposts(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<TweetResponseDto> getMentions(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//////////////////////////////////////////////////////////////////////////////////
	

	@Override
	public List<TweetResponseDto> getTweets() {
		
		List<Tweet> tweetList = tweetRepository.findAllByDeletedFalse();
		
		List<Tweet> chronoOrder = null;
			
		  for(int x = 0; x < tweetList.size(); x++) {
			  
			  
			if(tweetList.get(x).getPosted().getTimestamp().after((tweetList.get(x+1).getPosted().getTimestamp()))) {
				
				chronoOrder.add(tweetList.get(x+1));
				
			}
		}
		
	    return tweetMapper.entitiesToDtos(tweetRepository.saveAllAndFlush(chronoOrder));

	}

	
	@Override
	public TweetResponseDto deleteTweet(Long id, TweetRequestDto tweetRequestDto) {
		
		//TweetResponseDto tweetToDelete = getTweet(id);

		Optional<Tweet> tToDel = tweetRepository.findByIdAndDeletedFalse(id);
		

		if(!tToDel.isPresent() || !tweetRequestDto.getCredentials().equals(tToDel.get().getAuthor())) {
			
			
			throw new NotFoundException("No tweet found with id: " + id);

			
		}
		
		tToDel.get().setDeleted(true);

	
		return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tToDel.get()));

		
	}
	
	
	@Override
	public List<UserResponseDto> getLikes(@PathVariable Long id) {

		
		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		
		if(!optionalTweet.isPresent()) {
			

			throw new NotFoundException("No tweet found with id: " + id);

		}
	
		ArrayList<User> usersToReturn = new ArrayList<>();
		
		for(User u : userRepository.findAllByDeletedFalse()) {	// for all active users 
			
			if(u.getLikesTweetList().contains(optionalTweet)) {			// u liked OptionalTweet
				usersToReturn.add(u);
			}
		}
		
		return tweetMapper.entitiesToUserDtos(usersToReturn, HttpStatus.OK);
				

	}
	

	@Override
	public TweetResponseDto getTweet(Long id) {


		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		
		if(!optionalTweet.isPresent()) {
			

			throw new NotFoundException("No tweet found with id: " + id);

		}
				
		return tweetMapper.entityToDto(optionalTweet.get());
		
		//return optionalTweet.get();
		
	}
	
	
	@Override
	public ContextDto getContext(@PathVariable Long id) {

		
		TweetResponseDto fetchTweet = getTweet(id);
		
		if(fetchTweet == null) {
					
			throw new NotFoundException("No tweet found with : " + id);

		}		
		
		// 0 - get replies to tweet with id 
		
		List<Tweet> replies = new ArrayList<>();
		
		List<TweetResponseDto> fetchTweetReplies = getReplies(id);
		
		for(TweetResponseDto reply : fetchTweetReplies) {	// convert dto to tweet
			
			Tweet twt = tweetMapper.Dto_To_Entity(reply);
			
			replies.add(twt);
		}
		
		
		// 1 - check for deleted tweets & make after
				
		TweetResponseDto target = fetchTweet;
		
		List<Tweet> before = null, after = null;
		
		for(Tweet t : replies) {
			
			if(t.isDeleted() && !t.getInReplyTo().isDeleted()) {
				

				after.add(t.getInReplyTo());	// prepare after

			}
			
			if(getParentTweet(t) != null) {		// prepare before
				
				before.add(t);
				
			}
			
		}


		return tweetMapper.entitiesToContextDto();	// doubt here - how to return before, after and target in context

		
	}
	
	
	public Tweet getParentTweet(Tweet t) {		// helper method


		Long id = t.getId();
		
		if(!t.isDeleted() && !t.getInReplyTo().) {		// doubt here
			
			return t;
		}
				

	}

	@Override
	public List<TweetResponseDto> getReplies(Long id) {

		TweetResponseDto targetTweet = getTweet(id);
		
		List<TweetResponseDto> replies = null;

		if(targetTweet.getInReplyTo().isPresent()) {
			
			replies.add(targetTweet);
		}
		
		return replies;
	}




	

}
