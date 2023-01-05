package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import com.cooksys.twitter_api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    //
//    //GET users
//
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    //
//
//    //POST users
//
//
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    //GET users/@{username}

    @GetMapping("@{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    //
//    //PATCH users/@{username}
//
    @PatchMapping("@{username}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto updateUserProfile(@PathVariable String username, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUserProfile(username, userRequestDto);
    }
//
//    //DELETE users/@{username}
//
//
//    @DeleteMapping("{username}")
//    @ResponseStatus()
//    public UserResponseDto deleteUser(@PathVariable String username) {
//
//        return null;
//        // return userService.deleteUser(username);
//    }
//
//
//    //POST users/@{username}/follow
//
//    @PostMapping("{username}/follow")
//    @ResponseStatus()
//    public UserRequestDto follow(@PathVariable String username) {
//
//        return null;
//        // return userService.follow(username);
//
//    }
//
//
//    //POST users/@{username}/unfollow
//
//    @PostMapping("{username}/unfollow")
//    @ResponseStatus()
//    public UserRequestDto unfollow(@PathVariable String username) {
//
//        return null;
//        // return userService.unfollow(username);
//
//    }
//
//    //GET users/@{username}/feed
//
//    @GetMapping("{username}/feed")
//    @ResponseStatus()
//    public UserResponseDto feed(@PathVariable String username) {
//
//        return null;
//        // return userService.feed(username);
//    }
//
//
//    //GET users/@{username}/tweets
//
//    @GetMapping("{username}/tweets")
//    @ResponseStatus()
//    public UserResponseDto tweets(@PathVariable String username) {
//
//        return null;
//        // return userService.tweets(username);
//    }
//
//
//    //GET users/@{username}/mentions
//
//
//    @GetMapping("{username}/mentions")
//    @ResponseStatus()
//    public UserResponseDto mentions(@PathVariable String username) {
//
//        return null;
//        // return userService.mentions(username);
//    }
//
//
//    //GET users/@{username}/followers
//
//    @GetMapping("{username}/mentions")
//    @ResponseStatus()
//    public UserResponseDto followers(@PathVariable String username) {
//
//        return null;
//        // return userService.followers(username);
//    }
//
//
//    //GET users/@{username}/following
//
//
//    @GetMapping("{username}/mentions")
//    @ResponseStatus()
//    public UserResponseDto following(@PathVariable String username) {
//
//        return null;
//        // return userService.following(username);
//    }

}
