package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav"; //model
	private EditText bodyText; //model
	private ListView oldTweetsList; //model
	private TweetList tweets = new TweetList(); //model

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		//ArrayList<Tweet> tweetList = new ArrayList<Tweet>();

		//ImportantTweet importantTweet = new ImportantTweet(""); //
		//Tweet importantTweet2 = new ImportantTweet("");
		//Tweetable importantTweet3 = new ImportantTweet("");

        /*
        try {
            importantTweet2.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

		//importantTweet.getText();
		//importantTweet3.getText();


		super.onCreate(savedInstanceState); //model
		setContentView(R.layout.main); //view

		bodyText = (EditText) findViewById(R.id.body); //controller
		Button saveButton = (Button) findViewById(R.id.save); //controller
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); //view

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK); //model
				String text = bodyText.getText().toString(); //model
				saveInFile(text, new Date(System.currentTimeMillis())); //model
				finish(); //model

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart(); //model
		String[] tweets = loadFromFile(); //model
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, tweets); //model
		oldTweetsList.setAdapter(adapter); //model
	}

	private String[] loadFromFile() {
		ArrayList<String> tweets = new ArrayList<String>(); //model
		try {
			FileInputStream fis = openFileInput(FILENAME); //model
			BufferedReader in = new BufferedReader(new InputStreamReader(fis)); //model
			String line = in.readLine(); //model
			while (line != null) { //model
				tweets.add(line); //model
				line = in.readLine(); //model
			}

		} catch (FileNotFoundException e) { //model
			// TODO Auto-generated catch block
			e.printStackTrace(); //view
		} catch (IOException e) { //model
			// TODO Auto-generated catch block
			e.printStackTrace(); //view
		}
		return tweets.toArray(new String[tweets.size()]); //model
	}

	private void saveInFile(String text, Date date) { //model
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND); //model
			fos.write(new String(date.toString() + " | " + text)
					.getBytes()); //model
			fos.close(); //model
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //model
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //model
		}
	}
}