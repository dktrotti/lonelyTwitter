package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LonelyTwitterActivity extends Activity {
    private LonelyTwitterActivity activity = this;

    private static final String FILENAME = "file.sav"; // model
    private EditText bodyText; // model
    private ListView oldTweetsList; // model
    private ArrayAdapter<Tweet> adapter; //model
    private Button saveButton;

    public Button getSaveButton() {
        return saveButton;
    }

    public ArrayList<Tweet> getTweets() {
        return tweets;
    }

    public ListView getOldTweetsList() {
        return oldTweetsList;
    }

    private ArrayList<Tweet> tweets = new ArrayList<Tweet>(); // model

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); // model
        setContentView(R.layout.main); // view

        bodyText = (EditText) findViewById(R.id.body);  // controller ???
        saveButton = (Button) findViewById(R.id.save); // controller ??
        oldTweetsList = (ListView) findViewById(R.id.oldTweetsList); // controller?

        saveButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                setResult(RESULT_OK); // model
                String text = bodyText.getText().toString(); // model
                tweets.add(new MundaneTweet(text)); // model
                saveInFile(); // model
                adapter.notifyDataSetChanged(); // view ??
            }
        });

        oldTweetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, EditTweetActivity.class);
                //intent.putExtra("ca.ualberta.cs.lonelytwitter.editTweet", )
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart(); // model
        loadFromFile(); // model
        adapter = new ArrayAdapter<Tweet>(this, R.layout.list_item, tweets); // model
        oldTweetsList.setAdapter(adapter); // controller ??
        adapter.notifyDataSetChanged(); // view ??
    }

    private void loadFromFile() {
        //	tweets = new ArrayList<Tweet>();
        try {
            FileInputStream fis = openFileInput(FILENAME); // model
            BufferedReader in = new BufferedReader(new InputStreamReader(fis)); // model

            Gson gson = new Gson(); // model
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html
            Type arraylistType = new TypeToken<ArrayList<MundaneTweet>>() {}.getType(); // model
            tweets = gson.fromJson(in,arraylistType); // controller

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            tweets = new ArrayList<Tweet>(); // model
        }
    }

    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME, 0); // model
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos)); // model
            Gson gson = new Gson(); // model
            gson.toJson(tweets, out); //model
            //fos.write(text.getBytes());
            out.flush(); //model
            fos.close(); // model
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e); // model
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e); // model
        }
    }

    public void clearTweets(View v){
        tweets.clear(); // controller
        saveInFile(); // model
        adapter.notifyDataSetChanged(); // view
    }

    public EditText getBodyText() {
        return bodyText;
    }
}