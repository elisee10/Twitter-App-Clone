package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

public class TweetWithUser {
    //@Embedded notations flattens the properties of the user into the object. preserving encapsulation
    @Embedded
    User user;


    @Embedded(prefix  = "tweet_")
    Tweet tweet;

}
