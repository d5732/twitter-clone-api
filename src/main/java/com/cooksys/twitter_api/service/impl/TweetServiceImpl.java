package com.cooksys.twitter_api.service.impl;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.TweetService;
import com.cooksys.twitter_api.helpers.SortByPostedReverse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.*;

import static com.cooksys.twitter_api.helpers.Helpers.*;


@Service
@RequiredArgsConstructor
public class TweetServiceImpl implements TweetService {


    private final UserRepository userRepository;
    private final TweetMapper tweetMapper;
    private final TweetRepository tweetRepository;
    private final UserMapper userMapper;
    private final HashtagRepository hashtagRepository;

    /**
     * Creates a new simple tweet, with the author set to the user identified by the credentials in the request body.
     * If the given credentials do not match an active user in the database, an error should be sent in lieu of a
     * response.
     * <p>
     * The response should contain the newly-created tweet.
     * Because this always creates a simple tweet, it must have a content property and may not have inReplyTo or
     * repostOf properties.
     * <p>
     * IMPORTANT: when a tweet with content is created, the server must process the tweet's content for @{username}
     * mentions and #{hashtag} tags. There is no way to create hashtags or create mentions from the API, so this must be
     * handled automatically!
     * <p>
     * Request
     * {
     * content: 'string',
     * credentials: 'Credentials'
     * }
     * Response
     * 'Tweet'
     */
    @Override
    public TweetResponseDto createTweet(TweetRequestDto tweetRequestDto) {
      
    	CredentialsDto credentialsDto = tweetRequestDto.getCredentials();
        // 1. If the given credentials do not match an active user in the database, an error should be sent
        // 2. It must have a content property
        // Both are handled in the helper.
        if (!isValidTweetRequestDto(tweetRequestDto)) {
            throw new BadRequestException("Malformed tweet request");
        }

        // Get the author user if it's active
        Optional<User> optionalAuthor = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());

        // If the given credentials do not match an active user in the database, an error should be sent
        if (!credentialsAreCorrect(optionalAuthor, credentialsDto)) {
            throw new BadRequestException("Bad credentials or user is not active");
        }

        //     * IMPORTANT: when a tweet with content is created, the server must process the tweet's content for @{username}
        //     * mentions and #{hashtag} tags. There is no way to create hashtags or create mentions from the API, so this must be
        //     * handled automatically!

        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
        tweet.setAuthor(optionalAuthor.get());
        tweet.setPosted(new Timestamp(System.currentTimeMillis()));
        Tweet savedTweet = tweetRepository.saveAndFlush(tweet);
        parseAndSaveMentions(savedTweet, tweetRepository, userRepository); // inject dependencies
        parseAndSaveHashtags(savedTweet, tweetRepository, hashtagRepository); // inject dependencies
        return tweetMapper.entityToDto(savedTweet);
    }

    @Override
    public ResponseEntity<TweetRequestDto> createTweetReply(TweetRequestDto tweetRequestDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto) {

    	/*
    	 * 
    	 * 
    	 * Creates a reply tweet to the tweet with the given id. 
    	 * The author of the newly-created tweet should match the credentials provided by the request body. 
    	 * If the given tweet is deleted or otherwise doesn't exist, or if the given credentials do not match an active user 
    	 * in the database, an error should be sent in lieu of a response.
    	 * Because this creates a reply tweet, content is not optional. Additionally, notice 
    	 * that the inReplyTo property is not provided by the request. The server must create that relationship.
    	 * The response should contain the newly-created tweet. 
    	 * 
    	 * IMPORTANT: when a tweet with content is created, the server must process the tweet's 
    	 * content for @{username} mentions and #{hashtag} tags. There is no way to create hashtags or 
    	 * create mentions from the API, so this must be handled automatically!
    	 */
    	
    	// step 1 - get tweetToBeRepliedTo by id
    	// step 2 - check if tweetRequestDto is null -> if so throw exception
    	// step 3 - check if tweetToBeRepliedTo is deleted or doesn't exist and throw an error
    	// step 4 - check if the given credentials match the credentials 
    	
    	Optional<Tweet> tweetToBeRepliedTo = tweetRepository.findByIdAndDeletedFalse(id);		

    	
    	
    	return null;
    	
    }

    @Override
    public TweetResponseDto repostTweet(Long id, TweetRequestDto tweetRequestDto) {
		
    	
    	// tweetRequestDto - has credentials 
    	
    	//Creates a repost of the tweet with the given id. The author of the repost should match the credentials 
    	// provided in the request body. If the given tweet is deleted or otherwise doesn't exist, or 
    	// the given credentials do not match an active user in the database, an error should be sent in lieu of a response.

    	//Because this creates a repost tweet, content is not allowed. 
    	// Additionally, notice that the 
    	//repostOf property is not provided by the request. The server must create that relationship.

    	//The response should contain the newly-created tweet.
    	
    	
    	// 1. get tweet by id
    	// 2. check if tweet is null -> throw bad request
    	// 3. check if credentials is active -> use helper method
    	// 4. create a new tweet 
    	// 5. set isRepost(tweetWithId) Entity relationship
    	// 6. saveandflush
    	// 7. return
    	
    	Optional<Tweet> tweetToBeReposted = tweetRepository.findByIdAndDeletedFalse(id);		

    	// add null check for tweetRequestDto
    	
    	if(tweetRequestDto == null) {
    		
            throw new BadRequestException("Credentials cannot be null" + tweetRequestDto);	

    	}

    	CredentialsDto credentialsDto = tweetRequestDto.getCredentials();
    	
    	
        Optional<User> tweetAuthor = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());

        if (!credentialsAreCorrect(tweetAuthor, credentialsDto)) {
            throw new BadRequestException("Bad credentials or user is not active");
        }

        if (tweetToBeReposted.isEmpty() || tweetToBeReposted.get().isDeleted()) {


            throw new NotFoundException("No tweet found with id: " + id);		// fix error code

        }
       
        Tweet repostTweet = tweetMapper.dtoToEntity(tweetRequestDto);	
        
        repostTweet.setRepostOf(tweetToBeReposted.get());
               
        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(repostTweet));

    }

    @Override
    public ResponseEntity<TweetResponseDto> getTags(Long id) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<TweetResponseDto> getReposts(Long id) {

        //todo: untested method, waiting for post retweet
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("Tweet not found with id: " + id);
        }
        List<Tweet> allTweets = tweetRepository.findAllByDeletedFalse();



        ArrayList<Tweet> reposts = new ArrayList<>();
        for (Tweet tweet : allTweets) {
            if (tweet.getRepostOf().equals(optionalTweet.get())) {
                reposts.add(tweet);
            }
        }
        reposts.sort(new SortByPostedReverse());
        return tweetMapper.entitiesToDtos(reposts);
    }

    @Override
    public List<UserResponseDto> getMentions(Long id) {
        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);
        if (optionalTweet.isEmpty()) {
            throw new NotFoundException("Tweet not found with id: " + id);
        }
        List<User> mentionsUserList = new ArrayList<>(optionalTweet.get().getMentionsUserlist());

        ArrayList<User> del = new ArrayList<>();
        for (User u : mentionsUserList) {
            if (u.isDeleted()) {
                del.add(u);
            }
        }
        for (User u : del) {
            mentionsUserList.remove(u);
        }

        return userMapper.entitiesToDtos(mentionsUserList);
    }


    //////////////////////////////////////////////////////////////////////////////////


    @Override
    public List<TweetResponseDto> getTweets() {

        List<Tweet> tweetList = tweetRepository.findAllByDeletedFalse();

        tweetList.sort(new SortByPostedReverse());
        return tweetMapper.entitiesToDtos(tweetList);

    }


    @Override
    public TweetResponseDto deleteTweet(Long id, TweetRequestDto tweetRequestDto) {

        Optional<Tweet> tToDel = tweetRepository.findByIdAndDeletedFalse(id);


        if (!tToDel.isPresent() || !tweetRequestDto.getCredentials().equals(tToDel.get().getAuthor())) {


            throw new NotFoundException("No tweet found with id: " + id);


        }

        tToDel.get().setDeleted(true);


        return tweetMapper.entityToDto(tweetRepository.saveAndFlush(tToDel.get()));


    }

    @Override
    public List<UserResponseDto> getLikes(Long id) {


        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);

        if (!optionalTweet.isPresent()) {


            throw new NotFoundException("No tweet found with id: " + id);

        }

        ArrayList<User> usersToReturn = new ArrayList<>();

        for (User u : userRepository.findAllByDeletedFalse()) {    // for all active users

            if (u.getLikesTweetList().contains(optionalTweet)) {            // u liked OptionalTweet
                usersToReturn.add(u);
            }
        }

        return tweetMapper.entitiesToUserDtos(usersToReturn);


    }

    @Override
    public TweetResponseDto getTweet(Long id) {


        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);

        if (!optionalTweet.isPresent()) {


            throw new NotFoundException("No tweet found with id: " + id);

        }

        return tweetMapper.entityToDto(optionalTweet.get());

    }


    @Override
    public ContextDto getContext(Long id) {


        TweetResponseDto fetchTweet = getTweet(id);

        if (fetchTweet == null) {

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


        List<TweetResponseDto> fetchTweetReplies = getReplies(id);    // gets replies that are not deleted

        List<TweetResponseDto> before = null, after = null;

        for (TweetResponseDto t : fetchTweetReplies) {

            if (t.getInReplyTo() != null) {


                after.add(t.getInReplyTo());    // prepare after

                //TODO: warning about NullPointerException related to this conditional + replies.add(); secquence


            } else {

                before.add(t);                // prepare before

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
    public List<TweetResponseDto> getReplies(Long id) {        // entity to dtos

        TweetResponseDto targetTweet = getTweet(id);

        List<TweetResponseDto> replies = null;

        if (!(targetTweet.getInReplyTo().getContent().isEmpty() && targetTweet.getInReplyTo() == null)) {

            //TODO: warning about NullPointerException related to this conditional + replies.add(); secquence

            replies.add(targetTweet.getInReplyTo());

        } else {

            throw new BadRequestException("Tweet with id " + id + " doesn't exist or deleted");
        }


        return replies;
    }

    @Override
    public void likeTweet(Long id, UserRequestDto userRequestDto) {

    	
    	if (!userRepository.findByCredentialsUsernameAndDeletedFalse(userRequestDto.getCredentials().getUsername()).isPresent()) {


            throw new NotFoundException("Bad credentials with id: " + userRequestDto.getCredentials().getUsername());
        }
    	
    	
        Optional<Tweet> toBeLiked = tweetRepository.findByIdAndDeletedFalse(id);

        if (!toBeLiked.isPresent() || toBeLiked.get().isDeleted()) {


            throw new NotFoundException("No tweet found with id: " + id);

        }
        

        // get active user from db

        Optional<User> toLike = userRepository.findByCredentialsUsernameAndDeletedFalse(userRequestDto.getCredentials().getUsername());

        // setLike

        List<User> usersLikeTweetList = toBeLiked.get().getLikesUserList();

        usersLikeTweetList.add(toLike.get());

        toBeLiked.get().setLikesUserList(usersLikeTweetList);


    }


}