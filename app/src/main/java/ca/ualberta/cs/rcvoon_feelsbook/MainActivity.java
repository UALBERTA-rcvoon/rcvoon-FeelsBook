package ca.ualberta.cs.rcvoon_feelsbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import java.util.Calendar;
import java.util.Date;
/**
 * <h1>MainActivity</h1>
 * MainActivity shows the first screen of the app. On it, users can record their current emotions
 * by pressing a button corresponding to their current emotion. They may also record a comment
 * along with their emotion. Users can view the list of recorded emotions by going to the menu
 * in the upper-right hand corner of the activity and selecting "History". They may also
 * see the number of times each emotion has been recorded by selecting "Statistics".
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "EmotionRecords.sav";        //the filename to save data to.
    EmotionRecordListController erlc;

    /**
     * Creates the activity when the program is started and assigns listeners
     * to each of the 6 emotion buttons.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button joy_button = (Button) findViewById(R.id.joyButton);
        joy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Joy");
            }
        });
        Button love_button = (Button) findViewById(R.id.loveButton);
        love_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Love");
            }
        });
        Button surprise_button = (Button) findViewById(R.id.surpriseButton);
        surprise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Surprise");
            }
        });
        Button anger_button = (Button) findViewById(R.id.angerButton);
        anger_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Anger");
            }
        });
        Button sadness_button = (Button) findViewById(R.id.sadnessButton);
        sadness_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Sadness");
            }
        });
        Button fear_button = (Button) findViewById(R.id.fearButton);
        fear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                addRecord("Fear");
            }
        });
    }


    /**
     * Creates the option menu when the activity is made.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    /**
     * Switches to the emotion history activity by pressing the upper menu button.
     */
    public void viewHistory(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, EmotionRecordHistoryActivity.class);
        startActivity(intent);
    }
    /**
     * Switches to the emotion statistics activity by pressing the bottom menu button.
     */
    public void viewCount(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, EmotionRecordCountActivity.class);
        startActivity(intent);
    }

    /**
     * On start, load all of the saved EmotionRecords from a file.
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }
    /**
     * Using Gson, retrieve the list of EmotionRecords from a file.
     */
    private void loadFromFile() {
        try {
            EmotionRecordListController erlc = new EmotionRecordListController();
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<EmotionRecord>>() {}.getType();
            ArrayList<EmotionRecord> temp_emotions = gson.fromJson(in, listType);
            erlc.getEmotionRecordList().setEmotionRecords(temp_emotions);

        } catch (FileNotFoundException e) {
            erlc.getEmotionRecordList().setEmotionRecords(new ArrayList<EmotionRecord>());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    /**
     * Save the modified list of EmotionRecords to a file using Gson.
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(erlc.getEmotionRecordList().getEmotionRecords(), out);
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }
    /**
     * When a button is pressed, add an emotion record to the list. Makes a new EmotionRecord object
     * using the emotion corresponding to the pressed button, the current date, and the comment the user
     * may have inputted at the bottom of the screen.
     */
    public void addRecord(String new_emotion) {
        // Initialize a new emotion record object and emotion record list controller.
        EmotionRecord new_emotion_record = new EmotionRecord();
        EmotionRecordListController erlc = new EmotionRecordListController();
        // Get the current date.
        Date today = Calendar.getInstance().getTime();
        // Get the comment from the bottom text box and make it a string.
        EditText comment = (EditText) findViewById(R.id.addOptionalComment);
        String text = comment.getText().toString();
        // Make a new EmotionRecord object and add it to the list. Sort the list
        // afterwards and save the changes.
        if (text.length() <= EmotionRecord.getMAX_CHARS()) {
            if (new_emotion.equals("Joy")) {
                new_emotion_record = new EmotionRecord(new Joy(), today, text);
            }
            if (new_emotion.equals("Love")) {
                new_emotion_record = new EmotionRecord(new Love(), today, text);
            }
            if (new_emotion.equals("Surprise")) {
                new_emotion_record = new EmotionRecord(new Surprise(), today, text);
            }
            if (new_emotion.equals("Anger")) {
                new_emotion_record = new EmotionRecord(new Anger(), today, text);
            }
            if (new_emotion.equals("Sadness")) {
                new_emotion_record = new EmotionRecord(new Sadness(), today, text);
            }
            if (new_emotion.equals("Fear")) {
                new_emotion_record = new EmotionRecord(new Fear(), today, text);
            }
            erlc.addEmotionRecord(new_emotion_record);
            erlc.getEmotionRecordList().sortEmotionRecordsByDate();
            Toast.makeText(MainActivity.this,
                    "Your selected emotion was recorded!", Toast.LENGTH_SHORT).show();
            comment.setText("");
            saveInFile();
        }
        // Remind user that comments can only be less than 100 characters.
        else {
            Toast.makeText(MainActivity.this,
                    "Comments must be less than 100 characters.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}