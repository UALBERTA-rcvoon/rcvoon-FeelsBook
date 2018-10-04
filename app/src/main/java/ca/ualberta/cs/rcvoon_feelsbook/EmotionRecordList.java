package ca.ualberta.cs.rcvoon_feelsbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class EmotionRecordList {

    private ArrayList<EmotionRecord> emotion_record_list;
    private ArrayList<Listener> listeners;

    EmotionRecordList() {
        this.emotion_record_list = new ArrayList<EmotionRecord>();
        this.listeners = new ArrayList<Listener>();
    }

    private ArrayList<Listener> getListeners() {
        if(listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return listeners;
    }

    public Collection<EmotionRecord> getEmotionRecords() {
        return emotion_record_list;
    }

    public void editEmotionRecord(EmotionRecord emotion_record_to_change, int position) {
        this.emotion_record_list.set(position, emotion_record_to_change);
        notifyListeners();

    }

    public void setEmotionRecords(ArrayList<EmotionRecord> new_emotion_record_list) {
        this.emotion_record_list = new_emotion_record_list;
    }
    public void addEmotionRecord(EmotionRecord new_emotion_record) {
        this.emotion_record_list.add(new_emotion_record);
        notifyListeners();
    }
    public void removeEmotionRecord(EmotionRecord emotion_record_to_remove) {
        this.emotion_record_list.remove(emotion_record_to_remove);
        notifyListeners();
    }
    public int countEmotionFrequency(Emotion emotion_to_count) {
        int emotion_counter = 0;
        for (int i = 0; i < this.emotion_record_list.size(); i++) {
            if (emotion_record_list.get(i).getEmotion().getEmotionName().equals(emotion_to_count.getEmotionName())) {
                emotion_counter += 1;
            }
        }
        return emotion_counter;
    }
    public void sortEmotionRecordsByDate() {
        Collections.sort(this.emotion_record_list);
        notifyListeners();
    }
    private void notifyListeners() {
        for (Listener  listener : getListeners()) {
            listener.update();
        }
    }
    public void addListener(Listener l) {
        getListeners().add(l);
    }
    public void removeListener(Listener l) {
        getListeners().remove(l);
    }
}
