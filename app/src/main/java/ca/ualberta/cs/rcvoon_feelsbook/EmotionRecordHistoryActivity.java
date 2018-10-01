package ca.ualberta.cs.rcvoon_feelsbook;
//https://android--code.blogspot.com/2015/08/android-listview-itemrow-height.html
//https://www.journaldev.com/9976/android-date-time-picker-dialog
//https://stackoverflow.com/questions/35861081/custom-popup-dialog-with-input-field
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;


public class EmotionRecordHistoryActivity extends AppCompatActivity {
    private static final String FILENAME = "timp.sav";
    private int year, month, day, hour, minute, second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_record_history);

        Collection<EmotionRecord> emotion_records = EmotionRecordListController.getEmotionRecordList().getEmotionRecords();
        ListView previous_emotion_records_list = (ListView) findViewById(R.id.emotionRecordListView);
        final ArrayList<EmotionRecord> list = new ArrayList<EmotionRecord>(emotion_records);
        final ArrayAdapter<EmotionRecord> emotionRecordArrayAdapter = new ArrayAdapter<EmotionRecord>(this, android.R.layout.simple_list_item_1, list) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position,convertView,parent);
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = 200;
                view.setLayoutParams(params);
                return view;
            }
        };
        previous_emotion_records_list.setAdapter(emotionRecordArrayAdapter);

        EmotionRecordListController.getEmotionRecordList().addListener(new Listener() {
            @Override
            public void update() {
                list.clear();
                Collection<EmotionRecord> emotion_records = EmotionRecordListController.getEmotionRecordList().getEmotionRecords();
                list.addAll(emotion_records);

                saveInFile(list);
                emotionRecordArrayAdapter.notifyDataSetChanged();
            }
        });

        previous_emotion_records_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            String[] options = {"Delete record", "Edit emotion", "Edit date", "Edit comment"};
            AlertDialog.Builder adb = new AlertDialog.Builder(EmotionRecordHistoryActivity.this);
            adb.setTitle("Edit record?");
            adb.setCancelable(true);
            final int finalPosition = position;
            adb.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on options[which]
                    final EmotionRecord er = list.get(finalPosition);
                    if (which == 0) {
                        removeEmotionRecord(er);
                    }
                    if (which == 1) {
                        editEmotionRecordEmotion(er, finalPosition);
                    }
                    if (which == 2) {
                        editEmotionRecordDate(er, finalPosition);
                    }
                    if (which == 3) {
                        editEmotionRecordComment(er, finalPosition);
                    }
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            adb.show();
            return false;
        }
    });
    }
    public void removeEmotionRecord(EmotionRecord er) {
        EmotionRecordListController.getEmotionRecordList().removeEmotionRecord((er));
        Toast.makeText(EmotionRecordHistoryActivity.this,
                "Selected record was deleted successfully!", Toast.LENGTH_SHORT).show();
    }
    public void editEmotionRecordEmotion(final EmotionRecord er, final int position) {
        AlertDialog.Builder choose_new_emotion_adb = new AlertDialog.Builder(EmotionRecordHistoryActivity.this);
        choose_new_emotion_adb.setTitle("Choose a new emotion:");
        CharSequence[] emotions = new CharSequence[] {"Joy", "Love", "Surprise", "Anger", "Sadness", "Fear"};
        choose_new_emotion_adb.setSingleChoiceItems(emotions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Emotion temp_emotion = new Emotion();
                if (which == 0) { temp_emotion = new Joy(); }
                else if (which == 1) { temp_emotion = new Love(); }
                else if (which == 2) { temp_emotion = new Surprise(); }
                else if (which == 3) { temp_emotion = new Anger(); }
                else if (which == 4) { temp_emotion = new Sadness(); }
                else if (which == 5) { temp_emotion = new Fear(); }
                EmotionRecord temp_er = new EmotionRecord(temp_emotion, er.getDate(), er.getOptional_comment());
                EmotionRecordListController.getEmotionRecordList().editEmotionRecord(temp_er, position);
                dialog.dismiss();
                Toast.makeText(EmotionRecordHistoryActivity.this,
                        "Selected record's emotion was modified successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        choose_new_emotion_adb.setCancelable(true);
        AlertDialog choose_emotion_dialog = choose_new_emotion_adb.create();
        choose_emotion_dialog.show();
    }

    public void editEmotionRecordDate(final EmotionRecord er, final int position) {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(EmotionRecordHistoryActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour_of_day,
                                  int selected_minute) {
                hour = hour_of_day;
                minute = selected_minute;
                second = c.get(Calendar.SECOND);
                GregorianCalendar newGregorianCalendar = new GregorianCalendar(year, month, day, hour, minute, second);
                Date newDate = newGregorianCalendar.getTime();
                EmotionRecord temp_er = new EmotionRecord(er.getEmotion(), newDate,er.getOptional_comment());
                EmotionRecordListController.getEmotionRecordList().editEmotionRecord(temp_er, position);
                EmotionRecordListController.getEmotionRecordList().sortEmotionRecordsByDate();
                Toast.makeText(EmotionRecordHistoryActivity.this,
                        year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second, Toast.LENGTH_SHORT).show();
                Toast.makeText(EmotionRecordHistoryActivity.this,
                        "Selected record's date was modified successfully!", Toast.LENGTH_SHORT).show();
            }
        }, hour, minute, false);
        timePickerDialog.show();
        DatePickerDialog datePickerDialog = new DatePickerDialog(EmotionRecordHistoryActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selected_year, int month_of_year, int day_of_month) {
                year = selected_year;
                month = month_of_year;
                day = day_of_month;
            }
        }, year, month, day);
        datePickerDialog.show();
    }
    public void editEmotionRecordComment(final EmotionRecord er, final int position) {
        AlertDialog.Builder enter_new_comment_adb = new AlertDialog.Builder(EmotionRecordHistoryActivity.this);
        final EditText new_comment_input = new EditText(EmotionRecordHistoryActivity.this);
        new_comment_input.setInputType(InputType.TYPE_CLASS_TEXT);
        new_comment_input.setText(er.getOptional_comment());
        enter_new_comment_adb.setTitle("Edit your comment:");
        enter_new_comment_adb.setView(new_comment_input);
        enter_new_comment_adb.setCancelable(true);
        enter_new_comment_adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String temp_text = new_comment_input.getText().toString();
                if (temp_text.length() < er.getMAX_CHARS()) {
                    EmotionRecord temp_er = new EmotionRecord(er.getEmotion(), er.getDate(), temp_text);
                    EmotionRecordListController.getEmotionRecordList().editEmotionRecord(temp_er, position);
                    Toast.makeText(EmotionRecordHistoryActivity.this,
                            "Selected record's comment was modified successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(EmotionRecordHistoryActivity.this,
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
