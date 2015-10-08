package ca.ualberta.cs.lonelytwitter;

/**
 * Created by Dominic on 15-09-16.
 */
public class MundaneTweet extends Tweet {
    public MundaneTweet(String text) {
        super(text);
    }

    @Override
    public Boolean isImportant() {
        return Boolean.FALSE;
    }
}
