package com.cooksys.twitter_api.controllers;

import com.cooksys.twitter_api.service.TweetService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tweets")
public class TweetController {

    private final TweetService tweetService;

    /**
     * GET tweets endpoint
     * #60
     */
    @GetMapping
    public void getTweets() {
    }


    /**
     * POST tweets endpoint
     * #59
     */
    @PostMapping
    public void postTweets() {
    }


    /**
     * @d5732 GET tweets/{id} endpoint
     * #58
     */
    @GetMapping("/{id}/")
    public void getTweet() {
    }


    /**
     * @rhundal DELETE tweets/{id} endpoint
     * #57
     */
    @DeleteMapping("/{id}/")
    public void deleteTweet() {
    }


    /**
     * @d5732 POST tweets/{id}/like endpoint
     * #56
     */
    @PostMapping("/{id}/like")
    public void likeTweet() {
    }


    /**
     * @rhundal POST tweets/{id}/reply endpoint
     * #55
     */
    @PostMapping("/{id}/reply")
    public void replyToTweet() {
    }


    /**
     * @d5732 POST tweets/{id}/repost endpoint
     * #54
     */
    @PostMapping("/{id}/repost")
    public void repostTweet() {
    }


    /**
     * @d5732 GET tweets/{id}/tags endpoint
     * #53
     */
    @GetMapping("/{id}/tags")
    public void getTags() {
    }


    /**
     * @d5732 GET tweets/{id}/likes endpoint
     * #52
     */
    @GetMapping("/{id}/likes")
    public void getLikes() {
    }


    /**
     * @d5732 GET tweets/{id}/context endpoint
     * #51
     */
    @GetMapping("/{id}/context")
    public void getContext() {
    }


    /**
     * GET tweets/{id}/replies endpoint
     * #50
     */
    @GetMapping("/{id}/replies")
    public void getReplies() {
    }


    /**
     * GET tweets/{id}/reposts endpoint
     * #49
     */
    @GetMapping("/{id}/reposts")
    public void getReposts() {
    }


    /**
     * GET tweets/{id}/mentions endpoint
     * #48
     */
    @GetMapping("/{id}/mentions")
    public void getMentions() {
    }

    public static class ProfileResponseDto {
    }
}
