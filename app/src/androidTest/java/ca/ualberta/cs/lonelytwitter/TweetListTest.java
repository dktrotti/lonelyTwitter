package ca.ualberta.cs.lonelytwitter;

import android.test.ActivityInstrumentationTestCase2;

import java.util.Date;
import ca.ualberta.cs.lonelytwitter.MyObserver;

/**
 * Created by Dominic on 15-09-30.
 */
public class TweetListTest extends ActivityInstrumentationTestCase2 implements MyObserver{
    private boolean wasNotified = false;

    public TweetListTest() {
        super(LonelyTwitterActivity.class);
    }

    public void testDeleteTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        tweetList.delete(tweet);
        assertFalse(tweetList.hasTweet(tweet));
    }

    public void testAddTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        assertTrue(tweetList.hasTweet(tweet));
    }

    public void testGetTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        assertEquals(tweet, tweetList.getTweet(0));
    }

    public void testHasTweet() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        assertFalse(tweetList.hasTweet(tweet));
        tweetList.add(tweet);
        assertTrue(tweetList.hasTweet(tweet));
    }

    public void testGetCount() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        assertTrue(tweetList.getCount() == 1);
    }

    public void testAddException() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        try {
            tweetList.add(tweet);
            fail();
        } catch (IllegalArgumentException e) {
            //Pass
        }
    }

    public void testGetTweets() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.add(tweet);
        Tweet pastTweet = new MundaneTweet("Hello from the past.");
        pastTweet.setDate(new Date(1000));
        tweetList.add(pastTweet);
        TweetList sortedTweets = tweetList.getTweets();
        assertEquals(pastTweet, sortedTweets.getTweet(0));
        assertEquals(tweet, sortedTweets.getTweet(1));
    }

    public void testTweetListChanged() {
        TweetList tweetList = new TweetList();
        Tweet tweet = new MundaneTweet("Hello World.");
        tweetList.addObserver(this);
        wasNotified = false;
        assertFalse(wasNotified);
        tweetList.add(tweet);
        assertTrue(wasNotified);
    }

    public void myNotify() {
        wasNotified = true;
    }
}