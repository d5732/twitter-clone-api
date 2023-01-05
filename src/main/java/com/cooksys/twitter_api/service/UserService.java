package com.cooksys.twitter_api.service;

import com.cooksys.twitter_api.dtos.*;

import java.util.List;

public interface UserService {

    /**
     * GET users/@{username}
     * #42
     */
    UserResponseDto getUser(String username);

    /**
     * PATCH users/@{username}
     * #41
     */
    UserResponseDto updateUserProfile(String username, UserRequestDto userRequestDto);

    /**
     * DELETE users/@{username}
     * #40
     */
    UserResponseDto deleteUser(String username, CredentialsDto credentialsDto);

    /**
     * POST users/@{username}/follow
     * #39
     */
    void followUser(String username, CredentialsDto credentialsDto);

    /**
     * POST users/@{username}/unfollow
     * #38
     */
    void unfollowUser(String username, CredentialsDto credentialsDto);

    /**
     * GET users/@{username}/feed
     * #67
     */
    List<TweetResponseDto> getFeed(String username);

    /**
     * GET users/@{username}/tweets
     * #66
     */
    List<TweetResponseDto> getTweets(String username);

    /**
     * GET users/@{username}/mentions
     * #65
     */
    List<TweetResponseDto> getMentions(String username);

    /**
     * GET users/@{username}/followers
     * #64
     */
    List<UserResponseDto> getFollowers(String username);

    /**
     * GET users/@{username}/following
     * #63
     */
    List<UserResponseDto> getFollowing(String username);

    /**
     * GET users
     * #44
     */
    List<UserResponseDto> getAllUsers();

    /**
     * POST users
     * #43
     */
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
