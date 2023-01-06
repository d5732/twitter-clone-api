package com.cooksys.twitter_api.helpers;

import java.util.HashSet;
import java.util.regex.*;

import com.cooksys.twitter_api.dtos.CredentialsDto;
import com.cooksys.twitter_api.dtos.ProfileDto;
import com.cooksys.twitter_api.dtos.TweetRequestDto;
import com.cooksys.twitter_api.entities.User;

import java.util.Optional;
import java.util.Set;

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


    //parseMentions: Guard null pointer by validating the DTO before invoking this method
    public static Set<String> parseAndSaveMentions(TweetRequestDto tweetRequestDto) {
        String content = tweetRequestDto.getContent();
        Matcher matcher = mentionPattern.matcher(content);
        HashSet<String> result = new HashSet<>();
        while (matcher.find()) {
            result.add(content.substring(matcher.start(), matcher.end()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~ parseAndSaveMentions created substring set: " + result);

        //todo: implement save to database
        return result;
    }

    //parseMentions: Guard null pointer by validating the DTO before invoking this method
    public static Set<String> parseAndSaveHashtags(TweetRequestDto tweetRequestDto) {
        String content = tweetRequestDto.getContent();
        Matcher matcher = hashtagPattern.matcher(content);
        HashSet<String> result = new HashSet<>();
        while (matcher.find()) {
            result.add(content.substring(matcher.start(), matcher.end()));
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~ parseAndSaveHashtags created substring set: " + result);


        //todo: implement save to database
        return result;
    }

//    public class RegexExample8 {
//
////        boolean found = false;
//        while(matcher.find())
//
//        {
////            found = true;
//        }
////        if(!found){
////            System.out.println("No match found.");
////        }
//    }
//}

}
