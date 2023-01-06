package com.cooksys.twitter_api.helpers;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.*;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.entities.Hashtag;
import com.cooksys.twitter_api.entities.Tweet;
import com.cooksys.twitter_api.entities.User;
import com.cooksys.twitter_api.repositories.HashtagRepository;
import com.cooksys.twitter_api.repositories.TweetRepository;
import com.cooksys.twitter_api.repositories.UserRepository;

public class Helpers {


    public static boolean credentialsAreCorrect(Optional<User> optionalUser, CredentialsDto credentialsDto) {
        return optionalUser.isPresent()
                && isValidCredentialsDto(credentialsDto)
                && optionalUser.get().getCredentials().getUsername().equals(credentialsDto.getUsername())
                && optionalUser.get().getCredentials().getPassword().equals(credentialsDto.getPassword());
    }

    public static boolean isValidProfileDto(ProfileDto profileDto) {
        return profileDto != null
                && profileDto.getEmail() != null
                && !profileDto.getEmail().isEmpty();
    }

    public static boolean isValidCredentialsDto(CredentialsDto credentialsDto) {
        return credentialsDto != null
                && credentialsDto.getUsername() != null
                && credentialsDto.getPassword() != null
                && !credentialsDto.getUsername().isEmpty()
                && !credentialsDto.getPassword().isEmpty();
    }

    public static boolean isValidTweetRequestDto(TweetRequestDto tweetRequestDto) {
        return tweetRequestDto != null
                && tweetRequestDto.getContent() != null
                && isValidCredentialsDto(tweetRequestDto.getCredentials());
    }


    private static final Pattern mentionPattern = Pattern.compile("@[a-zA-Z0-9]+");
    private static final Pattern hashtagPattern = Pattern.compile("#[a-zA-Z0-9]+");


    public static void parseAndSaveMentions(Tweet tweet, TweetRepository tweetRepository, UserRepository userRepository) {
        //  repositories injected to update DB user_table table and the join table for user_mentions
        String content = tweet.getContent();
        Matcher matcher = mentionPattern.matcher(content);
        HashSet<String> mentions = new HashSet<>();
        while (matcher.find()) {
            mentions.add(content.substring(matcher.start()+1, matcher.end()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~ parseAndSaveMentions created mentions string set: " + mentions);
        if (mentions.size() > 0) {
            HashSet<User> usersToSave = new HashSet<>();
            for (String mention : mentions) {
                Optional<User> optionalMentioned = userRepository.findByCredentialsUsernameAndDeletedFalse(mention);
                optionalMentioned.ifPresent(user -> user.getMentionsTweetList().add(tweet));
            }
            tweet.setMentionsUserlist(new ArrayList<>(usersToSave));
            tweetRepository.saveAndFlush(tweet);
            userRepository.saveAllAndFlush(usersToSave);
        }
    }

    public static void parseAndSaveHashtags(Tweet tweet, TweetRepository tweetRepository, HashtagRepository hashtagRepository) {
        // repositories injected to update DB tweet table, hashtag table, and the join table for tweet_hashtags
        String content = tweet.getContent();
        Matcher matcher = hashtagPattern.matcher(content);
        HashSet<String> labels = new HashSet<>();
        while (matcher.find()) {
            labels.add(content.substring(matcher.start()+1, matcher.end()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~ parseAndSaveHashtags created labels string set: " + labels);
        if (labels.size() > 0) {
            HashSet<Hashtag> hashtagsToSave = new HashSet<>();
            for (String label : labels) {
                Hashtag hashtag;
                Optional<Hashtag> optionalHashtag = hashtagRepository.findByLabelAndDeletedFalse(label);
                if (optionalHashtag.isPresent()) {
                    // active Hashtag
                    hashtag = optionalHashtag.get();
                    // mutate tweetList
                    hashtag.getTweetList().add(tweet);
                } else {
                    // new Hashtag
                    hashtag = new Hashtag();
                    hashtag.setLabel(label);
                    hashtag.setFirstUsed(new Timestamp(System.currentTimeMillis()));
                    // init tweetList with current tweet
                    ArrayList<Tweet> newTweetList = new ArrayList<>();
                    newTweetList.add(tweet);
                    hashtag.setTweetList(newTweetList);
                }
                hashtag.setLastUsed(new Timestamp(System.currentTimeMillis()));
                hashtagsToSave.add(hashtag);
            }

            List<Hashtag> savedHashtagsList = hashtagRepository.saveAllAndFlush(hashtagsToSave);
            tweet.setHashtagList(savedHashtagsList);
            tweet.setHashtagList(new ArrayList<>(hashtagsToSave));
            tweetRepository.saveAndFlush(tweet);
        }
    }

}