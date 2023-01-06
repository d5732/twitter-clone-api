package com.cooksys.twitter_api.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.dtos.ContextDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;

import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.service.TweetService;
import java.util.Comparator;

import java.sql.Timestamp;  

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService{

		
	private final UserRepository userRepository;
	private final TweetMapper tweetMapper;
	private final TweetRepository tweetRepository;

	private static class SortByPostedReverse implements Comparator<Tweet> {
        public int compare(Tweet a, Tweet b) {
            return (int) (b.getPosted().getTime() - a.getPosted().getTime());
        }
    }
	 
	@Override
	public ResponseEntity<TweetResponseDto> postTweets(TweetResponseDto tweetResponseDto) {
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
			  
			  tweetList.sort(new SortByPostedReverse());
		        return tweetMapper.entitiesToDtos(tweetList);
			  

	}

	
	@Override
	public TweetResponseDto deleteTweet(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto) {
		
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
		
		return tweetMapper.entitiesToUserDtos(usersToReturn);
						

	}
	
	@Override
	public TweetResponseDto getTweet(@PathVariable Long id) {


		Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
		
		if(!optionalTweet.isPresent()) {
			

			throw new NotFoundException("No tweet found with id: " + id);

		}
				
		return tweetMapper.entityToDto(optionalTweet.get());
				
	}
	
	
	@Override
	public ContextDto getContext(@PathVariable Long id) {

		
		TweetResponseDto fetchTweet = getTweet(id);
		
		if(fetchTweet == null) {
					
			throw new NotFoundException("No tweet found with : " + id);

		}		
		
		// 1 - get replies to tweet with id 
		
		// dennis ideas - get all tweets 
		
		// find all tweets that point to null (where inReplyTo = null)
		
		// know about all deleted tweets
		// when u find the head whihc is has fetchTweet in its list as a reply, it is the head
		// H.getReplies() - will have all the tweets
		// sort H.getReplies ()
		// Bucket sort - before and after
		
		
		
		List<TweetResponseDto> fetchTweetReplies = getReplies(id);	// gets replies that are not deleted
		
		List<TweetResponseDto> before = null, after = null;
		
		for(TweetResponseDto t : fetchTweetReplies) {
			
			if(t.getInReplyTo() != null) {
				
				
				after.add(t.getInReplyTo());	// prepare after

			}else {
				
				before.add(t);				// prepare before

			}
			
		}
		
		// A - C 
	//	While deleted tweets should not be included in the before and after properties of the result, 
	//	transitive replies should. What that means is that if a reply to the target of the context is deleted, 
	//	but there's another reply to the deleted reply, the deleted reply should be excluded but the other reply should remain.

	//	return tweetMapper.entitiesToContextDto();	// doubt here - how to return before, after and target in context
		
		return null;
	}
	
	/*
	public Tweet getParentTweet(Tweet t) {		// helper method


		Long id = t.getId();
		
		if(!t.isDeleted() && !t.getInReplyTo().) {		// doubt here
			
			return t;
		}
		
		
		return null;

	}
	
	*/

	@Override
	public List<TweetResponseDto> getReplies(@PathVariable Long id) {		// entity to dtos

		TweetResponseDto targetTweet = getTweet(id);
		
		List<TweetResponseDto> replies = null;		
		
		if(!(targetTweet.getInReplyTo().getContent().isEmpty() && targetTweet.getInReplyTo() == null)) {
						
			replies.add(targetTweet.getInReplyTo());
			
		}else {
			
			throw new BadRequestException("Tweet with id " + id + " doesn't exist or deleted");
		}
		
		
		return replies;
	}

	@Override
	public void likeTweet(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
	
		
		Optional<Tweet> toBeLiked = tweetRepository.findByIdAndDeletedFalse(id);
		
		if(!toBeLiked.isPresent() || toBeLiked.get().isDeleted()) {
			

			throw new NotFoundException("No tweet found with id: " + id);

		}
		if(!userRepository.findByCredentialsUsernameAndDeletedFalse(userRequestDto.getCredentials().getUsername()).isPresent()) {
						
			
			throw new NotFoundException("Bad credentials with id: " + userRequestDto.getCredentials().getUsername());
		}
		
		// get active user from db
		
	    Optional<User> toLike = userRepository.findByCredentialsUsernameAndDeletedFalse(userRequestDto.getCredentials().getUsername());
	
		// setLike
		    
	    List<User> usersLikeTweetList = toBeLiked.get().getLikesUserList();		
	    
	    usersLikeTweetList.add(toLike.get());
	    
	    toBeLiked.get().setLikesUserList(usersLikeTweetList);

		
	}
	

}
