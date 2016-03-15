package com.example.jenny.represent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Jenny on 3/14/2016.
 */
public class getTweet extends AppCompatActivity{
    private static final String TWITTER_KEY = "1eRsdWZSK9OK2OcCiYiUKKAjg";
    private static final String TWITTER_SECRET = "dqdsYTnMcTgGBmtVbbCqab7zITVjYCBTrA8VwZvO0mrtZbEwXn";

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

}
