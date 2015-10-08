package ca.ualberta.cs.lonelytwitter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dominic on 15-09-16.
 */
public abstract class Tweet implements Tweetable{
    private String text;
    private Date date;
    private ArrayList<Mood> moodList = new ArrayList<Mood>();

    public Tweet(String text, ArrayList<Mood> moodList) {
        this.date = new Date();
        if (text.length() > 140) {

            this.text = text.substring(0, 140);
        } else {
            this.text = text;
        }
        this.moodList = moodList;
    }

    public Tweet(String text) {
        this.date = new Date();
        if (text.length() > 140) {

            this.text = text.substring(0, 140);
        } else {
            this.text = text;
        }
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return date.toString() + "||" + this.text.toString();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {this.date = date;}

    public void addMood(Mood mood) {
        moodList.add(mood);
    }

    public abstract Boolean isImportant();
}
