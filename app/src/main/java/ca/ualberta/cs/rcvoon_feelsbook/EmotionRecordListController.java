package ca.ualberta.cs.rcvoon_feelsbook;

import java.util.ArrayList;
import java.util.Arrays;

public class EmotionRecordListController {

    private static EmotionRecordList emotion_record_list = null;
    static public EmotionRecordList getEmotionRecordList() {
        if ((emotion_record_list) == null) {
            emotion_record_list = new EmotionRecordList();

        }
        return emotion_record_list;
    }

    static public void setEmotionRecordList(EmotionRecordList new_emotion_record_list) {
        emotion_record_list = new_emotion_record_list;
    }
    public void editEmotionRecord(EmotionRecord emotion_record_to_change, int position) {
        emotion_record_list.editEmotionRecord( emotion_record_to_change, position);
    }
    public void addEmotionRecord(EmotionRecord emotion_record_to_add) {
        emotion_record_list.addEmotionRecord(emotion_record_to_add);
    }
    public void sortEmotionRecordListByDate(){
        emotion_record_list.sortEmotionRecordsByDate();
    }

    public int[] getEmotionFrequencies(ArrayList<Emotion> emotion_names) {
        int counts[] = {0,0,0,0,0,0};
        EmotionRecordListController erlc = new EmotionRecordListController();
        for (int i=0; i<emotion_names.size(); i++ ) {
            counts[i] = erlc.getEmotionRecordList().countEmotionFrequency(emotion_names.get(i));
        }
        return counts;
    }

    public String[] getEmotionFrequencyStrings() {
        ArrayList<Emotion> emotion_names =  new ArrayList<>(Arrays.asList(new Joy(), new Love(), new Surprise(), new Anger(), new Sadness(), new Fear()));
        EmotionRecordListController erlc = new EmotionRecordListController();
        int[] counts = erlc.getEmotionFrequencies(emotion_names);
        String[] emotion_counts = new String[counts.length];
        for (int i = 0; i < counts.length; i++) {
            emotion_counts[i] = (emotion_names.get(i).format()
                    + " has been recorded " + counts[i] + " times.");
        }
        return emotion_counts;
    }
}
