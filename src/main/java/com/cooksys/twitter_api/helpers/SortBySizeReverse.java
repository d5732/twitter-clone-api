package com.cooksys.twitter_api.helpers;

import java.util.Comparator;
import java.util.List;

public class SortBySizeReverse implements Comparator<List> {
    public int compare(List a, List b) {
        return b.size() - a.size();
    }

}


