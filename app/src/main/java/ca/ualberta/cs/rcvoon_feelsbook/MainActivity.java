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

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "EmotionRecords.sav";
    EmotionRecordListController erlc;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void viewHistory(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, EmotionRecordHistoryActivity.class);
        startActivity(intent);
    }

    public void viewCount(MenuItem menu) {
        Intent intent = new Intent(MainActivity.this, EmotionRecordCountActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadFromFile();
    }
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
    public void addRecord(String new_emotion) {
        EmotionRecord new_emotion_record = new EmotionRecord();
        Date today = Calendar.getInstance().getTime();
        EmotionRecordListController erlc = new EmotionRecordListController();
        EditText comment = (EditText) findViewById(R.id.addOptionalComment);
        String text = comment.getText().toString();
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
        else {
            Toast.makeText(MainActivity.this,
                    "Comments must be less than 100 characters.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

}