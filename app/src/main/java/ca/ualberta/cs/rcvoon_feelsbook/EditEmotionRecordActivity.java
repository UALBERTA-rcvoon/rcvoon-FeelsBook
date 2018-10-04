package ca.ualberta.cs.rcvoon_feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public class EditEmotionRecordActivity extends AppCompatActivity {
    private static final String FILENAME = "EmotionRecords.sav";
    private int year, month, day, hour, minute, second;
    private Date new_date;
    private Emotion new_emotion;
    private String new_comment;
    private Collection<EmotionRecord> emotion_records = EmotionRecordListController.getEmotionRecordList().getEmotionRecords();
    private ArrayList<EmotionRecord> list = new ArrayList<EmotionRecord>(emotion_records);
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_emotion_record);
        Bundle resultIntent = getIntent().getExtras();
        if(resultIntent != null)
        {
            position = resultIntent.getInt("position");
            EmotionRecord temp_er = list.get(position);
            new_emotion = temp_er.getEmotion();
            new_date = temp_er.getDate();
            new_comment = temp_er.getOptional_comment();

        }
        EmotionRecordListController.getEmotionRecordList().addListener(new Listener() {
            @Override
            public void update() {

                list.clear();
                Collection<EmotionRecord> emotion_records = EmotionRecordListController.getEmotionRecordList().getEmotionRecords();
                list.addAll(emotion_records);
                saveInFile(list);

                }
        });

        updateTextView();
        final int selectedPosition = position;
        Button edit_emotion_button = (Button) findViewById(R.id.editEmotionButton);
        edit_emotion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                editEmotionRecordEmotion(selectedPosition);
            }
        });
        Button edit_date_button = (Button) findViewById(R.id.editDateButton);
        edit_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                editEmotionRecordDate(selectedPosition);
            }
        });
        Button edit_comment_button = (Button) findViewById(R.id.editCommentButton);
        edit_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                setResult(RESULT_OK);
                editEmotionRecordComment(selectedPosition);
            }
        });
    }

        public void editEmotionRecordEmotion(final int position) {
            AlertDialog.Builder choose_new_emotion_adb = new AlertDialog.Builder(EditEmotionRecordActivity.this);
            choose_new_emotion_adb.setTitle("Choose a new emotion:");
            CharSequence[] emotions = new CharSequence[] {"Joy", "Love", "Surprise", "Anger", "Sadness", "Fear"};
            int checked_item = -1;
            for (int i=0; i<emotions.length; i++) {
                if (new_emotion.getEmotionName().equals(emotions[i])) {
                    checked_item = i;
                }
            }
            choose_new_emotion_adb.setSingleChoiceItems(emotions, checked_item, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Emotion temp_emotion = new Emotion();
                    if (which == 0) { temp_emotion = new Joy(); }
                    else if (which == 1) { temp_emotion = new Love(); }
                    else if (which == 2) { temp_emotion = new Surprise(); }
                    else if (which == 3) { temp_emotion = new Anger(); }
                    else if (which == 4) { temp_emotion = new Sadness(); }
                    else if (which == 5) { temp_emotion = new Fear(); }
                    new_emotion = temp_emotion;
                    updateTextView();
                    updateEmotionRecord();
                    dialog.dismiss();
                    Toast.makeText(EditEmotionRecordActivity.this,
                            "Selected record's emotion was modified successfully!", Toast.LENGTH_SHORT).show();
                }
            });
            choose_new_emotion_adb.setCancelable(true);
            AlertDialog choose_emotion_dialog = choose_new_emotion_adb.create();
            choose_emotion_dialog.show();
        }

        public void editEmotionRecordDate(final int position) {
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditEmotionRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int selected_year, int month_of_year, int day_of_month) {
                    year = selected_year;
                    month = month_of_year;
                    day = day_of_month;
                    TimePickerDialog timePickerDialog = new TimePickerDialog(EditEmotionRecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour_of_day,
                                              int selected_minute) {
                            hour = hour_of_day;
                            minute = selected_minute;
                            second = c.get(Calendar.SECOND);
                            GregorianCalendar newGregorianCalendar = new GregorianCalendar(year, month, day, hour, minute, second);
                            new_date = newGregorianCalendar.getTime();
                            updateTextView();
                            updateEmotionRecord();
                            Toast.makeText(EditEmotionRecordActivity.this,
                                    year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                            Toast.makeText(EditEmotionRecordActivity.this,
                                    "Selected record's date was modified successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }, hour, minute, false);
                    timePickerDialog.show();
                }
            }, year, month, day);
            datePickerDialog.show();
        }
        public void editEmotionRecordComment(final int position) {
            AlertDialog.Builder enter_new_comment_adb = new AlertDialog.Builder(EditEmotionRecordActivity.this);
            final EditText new_comment_input = new EditText(EditEmotionRecordActivity.this);
            new_comment_input.setInputType(InputType.TYPE_CLASS_TEXT);
            new_comment_input.setText(new_comment);
            enter_new_comment_adb.setTitle("Edit your comment:");
            enter_new_comment_adb.setView(new_comment_input);
            enter_new_comment_adb.setCancelable(true);
            enter_new_comment_adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String temp_text = new_comment_input.getText().toString();
                    if (temp_text.length() < new EmotionRecord().getMAX_CHARS()) {
                        new_comment = temp_text;
                        updateTextView();
                        updateEmotionRecord();
                        Toast.makeText(EditEmotionRecordActivity.this,
                                "Selected record's comment was modified successfully!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(EditEmotionRecordActivity.this,
                                "Comments must be less than 100 characters.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            enter_new_comment_adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            enter_new_comment_adb.show();
        }

    public void updateTextView() {
        TextView emotion_text = (TextView) findViewById(R.id.editEmotionTextbox);
        TextView date_text = (TextView) findViewById(R.id.editDateTextbox);
        TextView comment_text = (TextView) findViewById(R.id.editCommentTextbox);
        emotion_text.setText(new_emotion.getEmotionName());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String formatted_date = df.format(new_date);
        date_text.setText(formatted_date);
        comment_text.setText(new_comment);
    }

    public void updateEmotionRecord() {
        EmotionRecord temp_er = new EmotionRecord(new_emotion, new_date, new_comment);
        EmotionRecordListController.getEmotionRecordList().editEmotionRecord(temp_er, position);
        saveInFile(list);

    }

    private void saveInFile(ArrayList<EmotionRecord> er_to_save) {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(er_to_save, out);
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

}
