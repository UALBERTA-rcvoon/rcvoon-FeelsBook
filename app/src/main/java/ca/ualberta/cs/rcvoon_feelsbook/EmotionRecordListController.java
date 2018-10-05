
package ca.ualberta.cs.rcvoon_feelsbook;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * <h1>EmotionRecordListController</h1>
 * EmotionRecordListController controls access to the EmotionRecordList object and keeps the activities
 * separate from the data stored in the list. It mainly retrieves the EmotionRecordList object, but
 * it also has a number of methods which perform operations on the EmotionRecordList.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class EmotionRecordListController {

    private static EmotionRecordList emotion_record_list = null;
    /**
     * Checks if list of EmotionRecords is unintialized, and if so
     * makes a instance.
     * @return the EmotionRecordList object.
     */
    static public EmotionRecordList getEmotionRecordList() {
        if ((emotion_record_list) == null) {
            emotion_record_list = new EmotionRecordList();

        }
        return emotion_record_list;
    }
    /**
     * Adds an EmotionRecord to the EmotionRecordList object.
     * @param emotion_record_to_add the new emotion record.
     */
    public void addEmotionRecord(EmotionRecord emotion_record_to_add) {
        emotion_record_list.addEmotionRecord(emotion_record_to_add);
    }

    /**
     * Gets the frequencies as integers of all 6 of the implemented kinds of emotions.
     * @param emotion_names an array of Emotion objects (or objects that extend the Emotion class)
     * @return counts an array of integers corresponding to the number of times
     * each emotion was recorded.
     */
    public int[] getEmotionFrequencies(ArrayList<Emotion> emotion_names) {
        int counts[] = {0,0,0,0,0,0};
        EmotionRecordListController erlc = new EmotionRecordListController();
        for (int i=0; i<emotion_names.size(); i++ ) {
            counts[i] = erlc.getEmotionRecordList().countEmotionFrequency(emotion_names.get(i));
        }
        return counts;
    }
    /**
     * Gets the frequencies of all 6 of the implemented kinds of emotions as strings.
     * @return emotion_counts an array of strings corresponding to the number of times
     * each emotion was recorded.
     */
    public String[] getEmotionFrequencyStrings() {
        ArrayList<Emotion> emotion_names =  new ArrayList<>(Arrays.asList(new Joy(), new Love(), new Surprise(), new Anger(), new Sadness(), new Fear()));
        EmotionRecordListController erlc = new EmotionRecordListController();
        int[] counts = erlc.getEmotionFrequencies(emotion_names);
        String[] emotion_counts = new String[counts.length];
        for (int i = 0; i < counts.length; i++) {
            emotion_counts[i] = (Integer.toString(counts[i]));
        }
        return emotion_counts;
    }
}
