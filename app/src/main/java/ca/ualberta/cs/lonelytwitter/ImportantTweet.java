package ca.ualberta.cs.lonelytwitter;

/**
 * Created by Dominic on 15-09-16.
 */
public class ImportantTweet extends Tweet {
    public ImportantTweet(String text) {
        super(text);
    }

    @Override
    public Boolean isImportant() {
        return Boolean.TRUE;
    }
}
