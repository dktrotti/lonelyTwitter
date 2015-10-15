package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import junit.framework.TestCase;

/**
 * Created by wz on 14/09/15.
 */
public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {

    private EditText bodytext;
    private Button saveButton;
    private EditText editText;
    private Button editButton;

    public LonelyTwitterActivityTest() {
        super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

//    public void testEditTweet() {
//        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();
//
//        activity.getTweets().clear();
//
//        bodytext = activity.getBodyText();
//        activity.runOnUiThread(new Runnable() {
//            public void run() {
//                bodytext.setText("Test tweet, please ignore.");
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//
//        saveButton = activity.getSaveButton();
//        activity.runOnUiThread(new Runnable() {
//            public void run() {
//                saveButton.performClick();
//            }
//        });
//        getInstrumentation().waitForIdleSync();
//
//        ListView oldtweets = activity.getOldTweetsList();
//        Tweet tweet = (Tweet) oldtweets.getItemAtPosition(0);
//        assertEquals("Test tweet, please ignore.", tweet.getText());
//    }

    public void testEditTweet(){

        // when you call getActivity the sys will start up the app & the activity.
        LonelyTwitterActivity activity = (LonelyTwitterActivity) getActivity();

        // reset the app to a known state
        activity.getTweets().clear();

        // add a tweet using UI

        bodytext = activity.getBodyText();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                bodytext.setText("Test tweet, please ignore.");
            }
        });
        getInstrumentation().waitForIdleSync();

        // save it
        saveButton = activity.getSaveButton();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                saveButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();


        // make sure the tweet has been added - use the UI to get it!!
        // ListView is describing the tweets on the screen
        final ListView oldTweetsList  = activity.getOldTweetsList();
        Tweet tweet = (Tweet) oldTweetsList.getItemAtPosition(0);

        assertEquals("Test tweet, please ignore.", tweet.getText());

        // ------ Next Test ---------

        // click on the tweet to edit
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();

        // https://developer.android.com/training/activity-testing/activity-functional-testing.html Oct 14 2015

        // Set up an ActivityMonitor
        Instrumentation.ActivityMonitor receiverActivityMonitor = getInstrumentation().addMonitor(EditTweetActivity.class.getName(), null, false);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                View v = oldTweetsList.getChildAt(0);
                oldTweetsList.performItemClick(v, 0, v.getId());
            }
        });
        getInstrumentation().waitForIdleSync();


        // Validate that ReceiverActivity is started
        //TouchUtils.clickView(this, sendToReceiverButton);
        EditTweetActivity receiverActivity = (EditTweetActivity) receiverActivityMonitor.waitForActivityWithTimeout(1000);

        assertNotNull("ReceiverActivity is null", receiverActivity);
        assertEquals("Monitor for ReceiverActivity has not been called", 1, receiverActivityMonitor.getHits());
        assertEquals("Activity is of wrong type", EditTweetActivity.class, receiverActivity.getClass());

        // Remove the ActivityMonitor
        getInstrumentation().removeMonitor(receiverActivityMonitor);

        // test that the editor starts up with the right tweet
        Tweet editTweet = receiverActivity.getTweet();
        assertEquals("Test tweet, please ignore.", editTweet.getText());

        //test that we can edit the tweet
        editText = receiverActivity.getEditText();
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                editText.setText("Test tweet, don't ignore.");
            }
        });
        getInstrumentation().waitForIdleSync();
        assertEquals("Test tweet, don't ignore.", editTweet.getText());

        //test that we can push some kind of save button
        editButton = receiverActivity.getEditButton();
        receiverActivity.runOnUiThread(new Runnable() {
            public void run() {
                editButton.performClick();
            }
        });
        getInstrumentation().waitForIdleSync();
        assertFalse(receiverActivity.isOpen());

        //test that the new tweet was saved
        Tweet savedTweet = activity.getTweets().get(0);
        assertEquals("Test tweet, don't ignore.", savedTweet.getText());

        //test that the modified tweet is displayed
        Tweet displayedTweet = (Tweet) oldTweetsList.getItemAtPosition(0);
        assertEquals("Test tweet, don't ignore.", displayedTweet.getText());

        // clean up activities
        receiverActivity.finish();
    }
}