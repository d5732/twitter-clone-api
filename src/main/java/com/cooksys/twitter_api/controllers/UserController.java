package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.TweetResponseDto;
import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }


    @GetMapping("@{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @PatchMapping("@{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUserProfile(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUserProfile(username, userRequestDto);
    }

    @DeleteMapping("@{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto deleteUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        return userService.deleteUser(username, credentialsDto);
    }

    @PostMapping("@{username}/follow")
    @ResponseStatus(HttpStatus.OK)
    public void follow(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        userService.followUser(username, credentialsDto);
    }

    @PostMapping("@{username}/unfollow")
    @ResponseStatus(HttpStatus.OK)
    public void unfollowUser(@PathVariable String username, @RequestBody CredentialsDto credentialsDto) {
        userService.unfollowUser(username, credentialsDto);
    }

    @GetMapping("@{username}/feed")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponseDto> getFeed(@PathVariable String username) {
        return userService.getFeed(username);
    }

    @GetMapping("@{username}/tweets")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponseDto> getTweets(@PathVariable String username) {
        return userService.getTweets(username);
    }

    @GetMapping("@{username}/mentions")
    @ResponseStatus(HttpStatus.OK)
    public List<TweetResponseDto> getMentions(@PathVariable String username) {
        return userService.getMentions(username);
    }

    @GetMapping("@{username}/followers")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @GetMapping("@{username}/following")
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
    }

}
