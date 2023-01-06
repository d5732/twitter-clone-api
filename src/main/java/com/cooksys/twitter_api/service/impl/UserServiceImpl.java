package com.cooksys.twitter_api.service.impl;

import com.cooksys.twitter_api.dtos.*;
import com.cooksys.twitter_api.entities.Credentials;
import com.cooksys.twitter_api.entities.Profile;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.exceptions.BadRequestException;
import com.cooksys.twitter_api.exceptions.NotFoundException;
import com.cooksys.twitter_api.mappers.CredentialsMapper;
import com.cooksys.twitter_api.mappers.ProfileMapper;
import com.cooksys.twitter_api.mappers.UserMapper;
import com.cooksys.twitter_api.repositories.UserRepository;
import com.cooksys.twitter_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    //    private final TweetMapper tweetMapper; //todo: fix;
    private final ProfileMapper profileMapper;
    private final CredentialsMapper credentialsMapper;

    private static class SortByPostedReverse implements Comparator<Tweet> {
        public int compare(Tweet a, Tweet b) {
            return (int) (b.getPosted().getTime() - a.getPosted().getTime());
        }
    }

    boolean credentialsAreCorrect(Optional<User> optionalUser, CredentialsDto credentialsDto) {
        return optionalUser.isPresent() &&
                optionalUser.get().getCredentials().getUsername().equals(credentialsDto.getUsername()) &&
                optionalUser.get().getCredentials().getPassword().equals(credentialsDto.getPassword());
    }

    private boolean isValid(ProfileDto profileDto) {
        return !profileDto.getEmail().isEmpty();
    }

    private boolean isValid(CredentialsDto credentialsDto) {
        return !credentialsDto.getUsername().isEmpty() && !credentialsDto.getPassword().isEmpty();
    }

    /**
     * GET users/@{username}
     * * Retrieves a user with the given username. If no such user exists or is deleted, an error should be sent in lieu
     * of a response.
     * #42
     */
    @Override
    public UserResponseDto getUser(String username) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        return userMapper.entityToDto(optionalUser.get());
    }

    /**
     * PATCH users/@{username}
     * Updates the profile of a user with the given username. If no such user exists, the user is deleted, or the
     * provided credentials do not match the user, an error should be sent in lieu of a response. In the case of a
     * successful update, the returned user should contain the updated data.
     *
     * TODO: NEVER UPDATE JOINED TIMESTAMP!!!!!!!!
     *
     * #41
     */
    @Override
    public UserResponseDto updateUserProfile(String username, CredentialsDto credentialsDto, ProfileDto profileDto) {
        Optional<User> optionalUser = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        if (!optionalUser.isPresent() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        optionalUser.get().setProfile(profileMapper.dtoToEntity(profileDto));
        userRepository.saveAndFlush(optionalUser.get());
        return userMapper.entityToDto(optionalUser.get());
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
        if (!optionalUser.isPresent() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
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
        if (!optionalUser.isPresent() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        if (!optionalUserToFollow.isPresent()) {
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
        if (!optionalUser.isPresent() || !credentialsAreCorrect(optionalUser, credentialsDto)) {
            throw new BadRequestException("Credentials provided do not match an active user in the database");
        }
        if (!optionalUserToUnfollow.isPresent()) {
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
        if (!optionalUser.isPresent()) {
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
//        return tweetMapper.entitiesToDtos(feed);
        //todo: fix;
        return null;
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
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(String.format("No active user found with username @%s", username));
        }
        ArrayList<Tweet> deleted = new ArrayList<>();
        for (Tweet tweet : optionalUser.get().getTweets()) {
            if (tweet.isDeleted()) {
                deleted.add(tweet);
            }
        }
        for (Tweet tweet : deleted) {
            optionalUser.get().getTweets().remove(tweet);
        }
        //TODO: Check sort order
        optionalUser.get().getMentionsTweetList().sort(new SortByPostedReverse());
//        return tweetMapper.entitiesToDtos(optionalUser.get().getTweets());
//    todo: fix or replace entitiesToDtos with for loop
        return null;
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
        if (!optionalUser.isPresent()) {
            throw new NotFoundException(String.format("User not found with username @%s", username));
        }
        ArrayList<Tweet> deleted = new ArrayList<>();
        for (Tweet tweet : optionalUser.get().getMentionsTweetList()) {
            if (!tweet.isDeleted()) {
                deleted.add(tweet);
            }
        }
        for (Tweet tweet : deleted) {
            optionalUser.get().getMentionsTweetList().remove(tweet);
        }
        //TODO: Check sort order
        optionalUser.get().getMentionsTweetList().sort(new SortByPostedReverse());
        return null;
        // TODO: fix
//        return tweetMapper.entitiesToDtos(optionalUser.get().getMentionsTweetList());
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
        if (!optionalUser.isPresent()) {
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
        if (!optionalUser.isPresent()) {
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
        if (!isValid(credentialsDto) || !isValid(profileDto)) {
            throw new BadRequestException("Required field(s) missing");
        }
        Optional<User> optionalUser = userRepository.findByCredentialsUsername(credentialsDto.getUsername());
        if (optionalUser.isPresent()) {
            if (!credentialsAreCorrect(optionalUser, credentialsDto)) {
                throw new BadRequestException("Username taken");
            }
            return userMapper.entityToDto(userRepository.saveAndFlush(optionalUser.get()));
        }
        Credentials credentials = credentialsMapper.dtoToEntity(credentialsDto);
        Profile profile = profileMapper.dtoToEntity(profileDto);
        // TODO: is there a Spring-like/mapper way to do this? Embeddeds complicate this
        User user = new User();
        user.setCredentials(credentials);
        profile.setJoined(new Timestamp(System.currentTimeMillis()));
        user.setProfile(profile);
        UserResponseDto res = userMapper.entityToDto(userRepository.saveAndFlush(user));
        System.out.println("~~~~~~~~~~~~~ " + res);
        res.setJoined(user.getProfile().getJoined());
        return res;
    }

}