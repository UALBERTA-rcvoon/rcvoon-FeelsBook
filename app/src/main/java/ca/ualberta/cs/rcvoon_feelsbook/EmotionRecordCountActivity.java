package ca.ualberta.cs.rcvoon_feelsbook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;

public class EmotionRecordCountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion_record_count);
        displayEmotionFrequencies();
    }

    void displayEmotionFrequencies() {

        TextView joy_count = (TextView) findViewById(R.id.joyTextCount);
        TextView love_count = (TextView) findViewById(R.id.loveTextCount);
        TextView surprise_count = (TextView) findViewById(R.id.surpriseTextCount);
        TextView anger_count = (TextView) findViewById(R.id.angerTextCount);
        TextView sadness_count = (TextView) findViewById(R.id.sadnessTextCount);
        TextView fear_count = (TextView) findViewById(R.id.fearTextCount);

        TextView[] emotion_counts = new TextView[] { joy_count, love_count, surprise_count, anger_count, sadness_count, fear_count};

        EmotionRecordListController erlc = new EmotionRecordListController();

        String[] counts = erlc.getEmotionFrequencyStrings();
        for (int i = 0; i < emotion_counts.length; i++) {
            emotion_counts[i].setText(counts[i]);
        }
    }


}
