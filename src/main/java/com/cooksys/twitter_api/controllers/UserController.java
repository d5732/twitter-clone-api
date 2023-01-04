package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.dtos.UserRequestDto;
import com.cooksys.twitter_api.dtos.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
//
//
//	//  private final UserService userService;
//
//	//GET validate/username/exists/@{username}
//
//	@GetMapping("validate/username/exists/{username}")
//	public ResponseEntity<UserResponseDto> userNameExists(@PathVariable String username) {
//
//		//return userService.userNameExists(username);
//	}
//
//	//GET validate/username/available/@{username}
//
//	@GetMapping("validate/username/available/{username}")
//	public ResponseEntity<UserResponseDto> userNameAvailable(@PathVariable String username) {
//
//		//return userService.userNameAvailable(username);
//	}
//
//
//	//GET users
//
//	@GetMapping
//	  public List<UserResponseDto> getAllUsers() {
//	  //  return userService.getAllUsers();
//	  }
//
//
//	//POST users
//
//
//	@PostMapping
//	public ResponseEntity<UserRequestDto> creatUser(@RequestBody UserRequestDto userRequestDto) {
//
//		//return userService.creatUser(userRequestDto);
//
//	}
//
//
//	//GET users/@{username}
//
//	@GetMapping("users/{username}")
//	public ResponseEntity<UserResponseDto> userByUserName(@PathVariable String username) {
//
//		//return userService.userByUserName(username);
//	}
//
//	//PATCH users/@{username}
//
//	@PatchMapping("users/{username}")
//	public UserRequestDto updateUserProfile(@PathVariable String username) {
//
//		//return userService.updateUserProfile(username);
//
//	}
//
//	//DELETE users/@{username}
//
//
//	@DeleteMapping("users/{username}")
//	public DeleteRequestDto deleteUser(@PathVariable String username) {
//
//	//	return userService.deleteUser(username);
//	}
//
//
//	//POST users/@{username}/follow
//
//	@PostMapping("users/{username}/follow")
//	public ResponseEntity<UserRequestDto> follow(@PathVariable String username) {
//
//		//return userService.follow(username);
//
//	}
//
//
//	//POST users/@{username}/unfollow
//
//	@PostMapping("users/{username}/unfollow")
//	public ResponseEntity<UserRequestDto> unfollow(@PathVariable String username) {
//
//		//return userService.unfollow(username);
//
//	}
//
//	//GET users/@{username}/feed
//
//	@GetMapping("users/{username}/feed")
//	public ResponseEntity<UserResponseDto> feed(@PathVariable String username) {
//
//		//return userService.feed(username);
//	}
//
//
//	//GET users/@{username}/tweets
//
//	@GetMapping("users/{username}/tweets")
//	public ResponseEntity<UserResponseDto> tweets(@PathVariable String username) {
//
//		//return userService.tweets(username);
//	}
//
//
//	//GET users/@{username}/mentions
//
//
//	@GetMapping("users/{username}/mentions")
//	public ResponseEntity<UserResponseDto> mentions(@PathVariable String username) {
//
//		//return userService.mentions(username);
//	}
//
//
//
//	//GET users/@{username}/followers
//
//	@GetMapping("users/{username}/mentions")
//	public ResponseEntity<UserResponseDto> followers(@PathVariable String username) {
//
//		//return userService.followers(username);
//	}
//
//
//	//GET users/@{username}/following
//
//
//	@GetMapping("users/{username}/mentions")
//	public ResponseEntity<UserResponseDto> following(@PathVariable String username) {
//
//		//return userService.following(username);
//	}




}
