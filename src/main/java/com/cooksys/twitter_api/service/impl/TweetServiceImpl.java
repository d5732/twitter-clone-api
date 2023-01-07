package com.cooksys.twitter_api.service.impl;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.helpers.SortByPostedReverse;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        optionalAuthor.get().getTweets().add(tweet);
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
    @Override
    public TweetResponseDto replyToTweet(Long id, TweetRequestDto tweetRequestDto) {

        // step 1 - get tweetToBeRepliedTo by id

        Optional<Tweet> tweetToBeRepliedTo = tweetRepository.findByIdAndDeletedFalse(id);

        // step 2 - check if tweetRequestDto is null -> if so throw exception

        if (tweetRequestDto == null) {
            throw new BadRequestException("Bad tweet request dto");
        }

        // step 3 - check if tweetToBeRepliedTo is deleted or doesn't exist and throw an error

        if (tweetToBeRepliedTo.isEmpty() || tweetRequestDto == null) {
            throw new BadRequestException("Bad request");
        }

        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse("");

        // step 4 - check if the given credentials match the credentials

        if (!credentialsAreCorrect(optionalUser, tweetRequestDto.getCredentials())) {
            throw new BadRequestException("BAD");
        }

        Tweet tweet = tweetMapper.dtoToEntity(tweetRequestDto);
        tweet.setInReplyTo(tweetToBeRepliedTo.get());
        tweet.setPosted(new Timestamp(System.currentTimeMillis()));
        tweet.setAuthor(optionalUser.get());
        parseAndSaveHashtags(tweet, tweetRepository, hashtagRepository);
        parseAndSaveMentions(tweet, tweetRepository, userRepository);
        return  tweetMapper.entityToDto(tweetRepository.saveAndFlush(tweet));
    }

    @Override
    public TweetResponseDto repostTweet(Long id, TweetRequestDto tweetRequestDto) {


        Optional<Tweet> tweetToBeReposted = tweetRepository.findByIdAndDeletedFalse(id);

        if (tweetRequestDto == null) {

            throw new BadRequestException("Credentials cannot be null" + tweetRequestDto);

        }

        CredentialsDto credentialsDto = tweetRequestDto.getCredentials();

        if (credentialsDto == null) {

            throw new BadRequestException("Bad Credentials DTO");
        }


        Optional<User> tweetAuthor = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());

        if (!credentialsAreCorrect(tweetAuthor, credentialsDto)) {
            throw new BadRequestException("Bad credentials or user is not active");
        }

        if (tweetToBeReposted.isEmpty() || tweetToBeReposted.get().isDeleted()) {


            throw new NotFoundException("No tweet found with id: " + id);        // fix error code

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
    public TweetResponseDto deleteTweet(Long id, CredentialsDto credentialsDto) {

        Optional<Tweet> tToDel = tweetRepository.findByIdAndDeletedFalse(id);


        if (!tToDel.isPresent() || !credentialsDto.getUsername().equals(tToDel.get().getAuthor().getCredentials().getUsername())) {


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
        
        
       
        /*
        for (User u : userRepository.findAllByDeletedFalse()) {    // for all active users

            if (u.getLikesTweetList().contains(optionalTweet)) {            // u liked OptionalTweet
                usersToReturn.add(u);
            }
        }
        */

        return tweetMapper.entitiesToUserDtos(optionalTweet.get().getLikesUserList());


    }

    @Override
    public TweetResponseDto getTweet(Long id) {


        Optional<Tweet> optionalTweet = tweetRepository.findByIdAndDeletedFalse(id);

        if (!optionalTweet.isPresent()) {


            throw new NotFoundException("No tweet found with id: " + id);

        }

        System.out.println("hello- ");

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
    public List<TweetResponseDto> getReplies(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndDeletedFalse(id);
        List<Tweet> allTweets = tweetRepository.findAllByDeletedFalse();
        if (optionalUser.isEmpty()) {
            throw new BadRequestException("bad user id");
        }

        ArrayList<Tweet> result = new ArrayList<>();
        for (Tweet tweet : allTweets) {
            if (tweet.getInReplyTo() != null && tweet.getInReplyTo().getAuthor() == optionalUser.get()) {
                result.add(tweet);
            }
        }
        return tweetMapper.entitiesToDtos(result);
    }

    @Override
    public void likeTweet(Long id, CredentialsDto credentialsDto) {

        Optional<User> liker = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());

        if (liker.isEmpty() || !credentialsAreCorrect(liker, credentialsDto)) {

            throw new BadRequestException("Bad request");
        }


        Optional<Tweet> toBeLiked = tweetRepository.findByIdAndDeletedFalse(id);

        if (!toBeLiked.isPresent() || toBeLiked.get().isDeleted()) {


            throw new NotFoundException("No tweet found with id: " + id);

        }


        if (!toBeLiked.get().getLikesUserList().contains(liker.get())) {

            toBeLiked.get().getLikesUserList().add(liker.get());
            tweetRepository.saveAndFlush(toBeLiked.get());


        }


        if (!liker.get().getLikesTweetList().contains(toBeLiked.get())) {

            liker.get().getLikesTweetList().add(toBeLiked.get());

            userRepository.saveAndFlush(liker.get());


        }


    }


}