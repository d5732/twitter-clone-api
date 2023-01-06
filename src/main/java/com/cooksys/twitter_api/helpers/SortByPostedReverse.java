package com.cooksys.twitter_api.helpers;

import com.cooksys.twitter_api.entities.Tweet;

import java.util.Comparator;

public class SortByPostedReverse implements Comparator<Tweet> {
    public int compare(Tweet a, Tweet b) {
        return (int) (b.getPosted().getTime() - a.getPosted().getTime());
    }

}



