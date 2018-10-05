package ca.ualberta.cs.rcvoon_feelsbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
/**
 * <h1>EmotionRecordCountActivity</h1>
 * EmotionRecordCountActivity displays the number of times each emotion has been recorded.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class EmotionRecordCountActivity extends AppCompatActivity {

    // On create, display the statistics.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_record_count);
        displayEmotionFrequencies();
    }

    // Set the number corresponding to each emotion to the
    void displayEmotionFrequencies() {
        // Get the textviews for each emotion and make an array with each of these textviews.
        TextView joy_count = (TextView) findViewById(R.id.joyTextCount);
        TextView love_count = (TextView) findViewById(R.id.loveTextCount);
        TextView surprise_count = (TextView) findViewById(R.id.surpriseTextCount);
        TextView anger_count = (TextView) findViewById(R.id.angerTextCount);
        TextView sadness_count = (TextView) findViewById(R.id.sadnessTextCount);
        TextView fear_count = (TextView) findViewById(R.id.fearTextCount);

        TextView[] emotion_counts = new TextView[] { joy_count, love_count, surprise_count, anger_count, sadness_count, fear_count};

        // Make a new EmotionRecordListController and calculate the counts of each emotion.
        EmotionRecordListController erlc = new EmotionRecordListController();
        String[] counts = erlc.getEmotionFrequencyStrings();

        // Set the numbers in the textviews for each emotion to the calculated values.
        for (int i = 0; i < emotion_counts.length; i++) {
            emotion_counts[i].setText(counts[i]);
        }
    }


}
