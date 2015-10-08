package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Dominic on 15-09-30.
 */
public class TweetList implements MyObservable{
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();
    private ArrayList<Tweet> tweets = new ArrayList<Tweet>();

    public TweetList() {}

    public TweetList(ArrayList<Tweet> initList) {
        tweets = initList;
    }

    public void add(Tweet tweet) {
        if (!this.hasTweet(tweet)) {
            tweets.add(tweet);
            notifyObservers();
        } else {
            throw new IllegalArgumentException("Duplicate tweet.");
        }
    }

    public boolean hasTweet(Tweet tweet) {
        return tweets.contains(tweet);
    }

    public void delete(Tweet tweet) {
        if (this.hasTweet(tweet)) {
            tweets.remove(tweet);
            notifyObservers();
        }
    }

    public Tweet getTweet(int index) {
        return tweets.get(index);
    }

    public int getCount() {
        return tweets.size();
    }

    public TweetList getTweets() {
        TweetList sortedTweets = new TweetList(this.tweets);
        sortedTweets.sort();
        return sortedTweets;
    }

    public void sort() {
        Collections.sort(tweets, new Comparator<Tweet>() {
            public int compare(Tweet t1, Tweet t2) {
                return t1.getDate().compareTo(t2.getDate());
            }
        });
    }

    public void addObserver(MyObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (MyObserver obs: observers) {
            obs.myNotify();
        }
    }
}
