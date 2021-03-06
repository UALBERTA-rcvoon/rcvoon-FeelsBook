package ca.ualberta.cs.rcvoon_feelsbook;
//https://android--code.blogspot.com/2015/08/android-listview-itemrow-height.html
//https://www.journaldev.com/9976/android-date-time-picker-dialog
//https://stackoverflow.com/questions/35861081/custom-popup-dialog-with-input-field

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>EmotionRecordHistoryActivity</h1>
 * EmotionRecordHistoryActivity displays all the emotions recorded by the user. By pressing on one
 * of the recorded emotions in the list, a user can delete the emotion record or edit its details.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class EmotionRecordHistoryActivity extends AppCompatActivity {
    private static final String FILENAME = "EmotionRecords.sav";        //the filename to save data to.

    /**
     * Creates the activity when the program is started. Displays the list of emotion records and
     * creates an ArrayAdapter to keep the list updated when changes occur. Sets up a long click listener
     * for each list item and lets the
     */
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
            String[] options = {"Delete", "Edit"};
            AlertDialog.Builder adb = new AlertDialog.Builder(EmotionRecordHistoryActivity.this);
            adb.setTitle("Edit item?");
            adb.setCancelable(true);
            final int final_position = position;
            adb.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on options[which]
                    final EmotionRecord er = list.get(final_position);
                    if (which == 0) {
                        removeEmotionRecord(er);
                    }
                    if (which == 1) {
                        editEmotionRecord(final_position);
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
    // Sort the list of emotion records upon resume.
    @Override
    public void onResume() {
        super.onResume();
        EmotionRecordListController.getEmotionRecordList().sortEmotionRecordsByDate();
    }
    /**
     * Switches to the edit emotion record activity when the edit option is chosen.
     */
    public void editEmotionRecord(int position) {
        Intent intent = new Intent(EmotionRecordHistoryActivity.this, EditEmotionRecordActivity.class);
        // pass the position of the selected emotion record to the new activity.
        intent.putExtra("position",position);
        startActivity(intent);
    }
    /**
     * Deletes an emotion record from the list.
     */
    public void removeEmotionRecord(EmotionRecord er) {
        EmotionRecordListController.getEmotionRecordList().removeEmotionRecord((er));
        Toast.makeText(EmotionRecordHistoryActivity.this,
                "Selected record was deleted successfully!", Toast.LENGTH_SHORT).show();
    }
    /**
     * Save the modified list of EmotionRecords to a file using Gson.
     */
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
