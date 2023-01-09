package com.cooksys.twitter_api.service.impl;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.helpers.SortByPostedReverse;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.TweetMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cooksys.twitter_api.helpers.Helpers.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TweetMapper tweetMapper;
    private final ProfileMapper profileMapper;
    private final CredentialsMapper credentialsMapper;


    /**
     * GET users/@{username}
     * * Retrieves a user with the given username. If no such user exists or is deleted, an error should be sent in lieu
     * of a response.
     * #42
     */
    @Override
    public UserResponseDto getUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        return userMapper.entityToDto(optionalUser.get());
    }

    /**
     * PATCH users/@{username}
     * Updates the profile of a user with the given username. If no such user exists, the user is deleted, or the
     * provided credentials do not match the user, an error should be sent in lieu of a response. In the case of a
     * successful update, the returned user should contain the updated data.
     * <p>
     * TODO: NEVER UPDATE JOINED TIMESTAMP!!!!!!!!
     * <p>
     * #41
     */
    @Override
    public UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto) {
        CredentialsDto credentialsDto = userRequestDto.getCredentials();
        ProfileDto profileDto = userRequestDto.getProfile();
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }

        /// {
        //  "profile":  {}
        //  }
//         or {} // i.e. profile field absent
        if (userRequestDto.getProfile() == null) {
            throw new BadRequestException("Bad profile in request");
        }

        // check the fields to override non nulls
        if (profileDto.getEmail() != null && !profileDto.getEmail().isEmpty()) { // 500
            optionalUser.get().getProfile().setEmail(profileDto.getEmail());
        }
        if (profileDto.getPhone() != null && !profileDto.getPhone().isEmpty()) {
            optionalUser.get().getProfile().setPhone(profileDto.getPhone());
        }
        if (profileDto.getFirstName() != null && !profileDto.getFirstName().isEmpty()) {
            optionalUser.get().getProfile().setFirstName(profileDto.getFirstName());
        }
        if (profileDto.getLastName() != null && !profileDto.getLastName().isEmpty()) {
            optionalUser.get().getProfile().setLastName(profileDto.getLastName());
        }
        //todo: UNIMPORTANT, but it'd be best practice to guard DB from a no-change saveAndFlush;
        return userMapper.entityToDto(userRepository.saveAndFlush(optionalUser.get()));
    }


    /**
     * DELETE users/@{username}
     * "Deletes" a user with the given username. If no such user exists or the provided credentials do not match the
     * user, an error should be sent in lieu of a response. If a user is successfully "deleted", the response should
     * contain the user data prior to deletion.
     * IMPORTANT: This action should not actually drop any records from the database! Instead, develop a way to keep
     * track of "deleted" users so that if a user is re-activated, all of their tweets and information are restored.
     * #40
     */
    @Override
    public UserResponseDto deleteUser(String username, CredentialsDto credentialsDto) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        optionalUser.get().setDeleted(true);
        userRepository.saveAndFlush(optionalUser.get());
        return userMapper.entityToDto(optionalUser.get());
    }


    /**
     * POST users/@{username}/follow
     * Subscribes the user whose credentials are provided by the request body to the user whose username is given in
     * the url. If there is already a following relationship between the two users, no such followable user exists
     * (deleted or never created), or the credentials provided do not match an active user in the database, an error
     * should be sent as a response. If successful, no data is sent.
     * #39
     */
    @Override
    public void followUser(String username, CredentialsDto credentialsDto) {
        Optional<User> optionalUserToFollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        if (optionalUser.isEmpty() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        if (optionalUserToFollow.isEmpty()) {
            throw new BadRequestException("No such followable user exists");
        }
        if (optionalUser.get().getFollowing().contains(optionalUserToFollow.get())) {
            throw new BadRequestException(String.format("Already following @%s", username));
        }
        ArrayList<User> followingList = new ArrayList<>(optionalUser.get().getFollowing());
        followingList.add(optionalUserToFollow.get());
        optionalUser.get().setFollowing(followingList);
        userRepository.saveAndFlush(optionalUser.get());
    }


    /**
     * POST users/@{username}/unfollow
     * Unsubscribes the user whose credentials are provided by the request body from the user whose username is given
     * in the url. If there is no preexisting following relationship between the two users, no such followable user
     * exists (deleted or never created), or the credentials provided do not match an active user in the database, an
     * error should be sent as a response. If successful, no data is sent.
     * #38
     */
    @Override
    public void unfollowUser(String username, CredentialsDto credentialsDto) {
        Optional<User> optionalUserToUnfollow = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(credentialsDto.getUsername());
        if (optionalUser.isEmpty() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        if (optionalUserToUnfollow.isEmpty()) {
            throw new BadRequestException("No such followable user exists");
        }
        if (!optionalUser.get().getFollowing().contains(optionalUserToUnfollow.get())) {
            throw new BadRequestException(String.format("Not following @%s", optionalUserToUnfollow.get().getCredentials().getUsername()));
        }
        ArrayList<User> followingList = new ArrayList<>(optionalUser.get().getFollowing());
        followingList.remove(optionalUserToUnfollow.get());
        optionalUser.get().setFollowing(followingList);
        userRepository.saveAndFlush(optionalUser.get());
    }

    /**
     * GET users/@{username}/feed
     * Retrieves all (non-deleted) tweets authored by the user with the given username, as well as all (non-deleted)
     * tweets authored by users the given user is following. This includes simple tweets, reposts, and replies. The
     * tweets should appear in reverse-chronological order. If no active user with that username exists (deleted or
     * never created), an error should be sent in lieu of a response.
     * #67
     */
    @Override
    public List<TweetResponseDto> getFeed(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        ArrayList<Tweet> feed = new ArrayList<>();
        ArrayList<Long> idsList = new ArrayList<>();
        for (User user : optionalUser.get().getFollowing()) {
            idsList.add(user.getId());
        }
        List<User> users = userRepository.findAllByDeletedFalseAndIdIn(idsList);
        users.add(optionalUser.get());
        for (User user : users) {
            feed.addAll(user.getTweets());
        }
        //TODO: Check sort order
        feed.sort(new SortByPostedReverse());
        return tweetMapper.entitiesToDtos(feed);
    }

    /**
     * GET users/@{username}/tweets
     * Retrieves all (non-deleted) tweets authored by the user with the given username. This includes simple tweets,
     * reposts, and replies. The tweets should appear in reverse-chronological order. If no active user with that
     * username exists (deleted or never created), an error should be sent in lieu of a response.
     * #66
     */
    @Override
    public List<TweetResponseDto> getTweets(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("No active user found with username @%s", username));
        }
        ArrayList<Tweet> result = new ArrayList<>();
        for (Tweet tweet : optionalUser.get().getTweets()) {
                if(!tweet.isDeleted()) {
                    result.add(tweet);
                }
        }
        result.sort(new SortByPostedReverse());
        return tweetMapper.entitiesToDtos(result);
    }


    /**
     * GET users/@{username}/mentions
     * Retrieves all (non-deleted) tweets in which the user with the given username is mentioned. The tweets should
     * appear in reverse-chronological order. If no active user with that username exists, an error should be sent in
     * lieu of a response.
     * A user is considered "mentioned" by a tweet if the tweet has content and the user's username appears in that
     * content following a @.
     * #65
     */
    @Override
    public List<TweetResponseDto> getMentions(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        ArrayList<Tweet> res = new ArrayList<>();
        for (Tweet tweet : optionalUser.get().getMentionsTweetList()) {
            if (!tweet.isDeleted()) {
                res.add(tweet);
            }
        }
        //TODO: Check sort order
        res.sort(new SortByPostedReverse());
        return tweetMapper.entitiesToDtos(res);
    }


    /**
     * GET users/@{username}/followers
     * Retrieves the followers of the user with the given username. Only active users should be included in the
     * response. If no active user with the given username exists, an error should be sent in lieu of a response.
     * #64
     */
    @Override
    public List<UserResponseDto> getFollowers(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        ArrayList<UserResponseDto> result = new ArrayList<>();
        for (User user : optionalUser.get().getFollowers()) {
            if (!user.isDeleted()) {
                result.add(userMapper.entityToDto(user));
            }
        }
        return result;
    }

    /**
     * GET users/@{username}/following
     * Retrieves the users followed by the user with the given username. Only active users should be included in the
     * response. If no active user with the given username exists, an error should be sent in lieu of a response.
     * #63
     */
    @Override
    public List<UserResponseDto> getFollowing(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        ArrayList<User> deleted = new ArrayList<>();
        for (User user : optionalUser.get().getFollowing()) {
            if (user.isDeleted()) {
                deleted.add(user);
            }
        }
        optionalUser.get().getFollowing().removeAll(deleted);
        ArrayList<UserResponseDto> result = new ArrayList<>();
        for (User user : optionalUser.get().getFollowing()) {
            result.add(userMapper.entityToDto(user));
        }
        return result;
    }


    /**
     * GET users
     * Retrieves all active (non-deleted) users as an array.
     * #44
     */
    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> usersList = userRepository.findAllByDeletedFalse();
        //Todo: This could be done cleaner with entitiesToDtos if we can get that mapper method to work.
        ArrayList<UserResponseDto> result = new ArrayList<>();
        for (User user : usersList) {
            result.add(userMapper.entityToDto(user));
        }
        return result;
    }

    /**
     * POST users
     * Creates a new user. If any required fields are missing or the username provided is already taken, an error should
     * be sent in lieu of a response.
     * If the given credentials match a previously-deleted user, re-activate the deleted user instead of creating a new
     * one.
     * #43
     */

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        CredentialsDto credentialsDto = userRequestDto.getCredentials();
        ProfileDto profileDto = userRequestDto.getProfile();
        if (!isValidCredentialsDto(credentialsDto) || !isValidProfileDto(profileDto)) {
            throw new BadRequestException("Required field(s) missing");
        }
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());
        if (optionalUser.isPresent()) {
            if (!credentialsAreCorrect(optionalUser, credentialsDto)) {
                throw new BadRequestException("Username taken");
            }
            if (!optionalUser.get().isDeleted()) {
                throw new BadRequestException("Username taken");
            }
            optionalUser.get().setDeleted(false);
            return userMapper.entityToDto(userRepository.saveAndFlush(optionalUser.get()));
        }
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        Profile profile = profileMapper.dtoToEntity(profileDto);
        // TODO: UNIMPORTANT: is there a Spring-like/mapper way to do this without new'ing? Embeddeds complicate this?
        //  Or is it as easy as dtoToEntity?
        User user = new User();
        user.setCredentials(credentials);
        user.setProfile(profile);
        user.setJoined(new Timestamp(System.currentTimeMillis()));
        return userMapper.entityToDto(userRepository.saveAndFlush(user));
    }

}
