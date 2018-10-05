package ca.ualberta.cs.rcvoon_feelsbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
/**
 * <h1>EmotionRecordList</h1>
 * EmotionRecordList contains a list of EmotionRecords and has several methods to
 * perform operations on that list of EmotionRecords. It also has a list of objects that
 * implement the Listener interface, which perform the update method whenever changes are
 * made to the list of EmotionRecords.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class EmotionRecordList {

    private ArrayList<EmotionRecord> emotion_record_list;
    private ArrayList<Listener> listeners;
    /**
     * Constructor. Creates a new list of EmotionRecord objects and a new list of objects
     * which implement Listener.
     */
    EmotionRecordList() {
        this.emotion_record_list = new ArrayList<EmotionRecord>();
        this.listeners = new ArrayList<Listener>();
    }
    /**
     * Accessor method. Checks if the list of listeners is uninitialized, and if so
     * makes a new list.
     * @return the list of objects which implement Listener.
     */
    private ArrayList<Listener> getListeners() {
        if(listeners == null) {
            listeners = new ArrayList<Listener>();
        }
        return listeners;
    }
    /**
     * Accessor method
     * @return the list of EmotionRecords.
     */
    public Collection<EmotionRecord> getEmotionRecords() {
        return emotion_record_list;
    }
    /**
     * Mutator method. Modifies an EmotionRecord at a specified position.
     * @param emotion_record_to_change the updated EmotionRecord.
     * @param position the location of the EmotionRecord to modify.
     */
    public void editEmotionRecord(EmotionRecord emotion_record_to_change, int position) {
        this.emotion_record_list.set(position, emotion_record_to_change);
        notifyListeners();

    }
    /**
     * Mutator method. Replaces the list of EmotionRecords with another list.
     * @param new_emotion_record_list the list of EmotionRecords to set this list to.
     */
    public void setEmotionRecords(ArrayList<EmotionRecord> new_emotion_record_list) {
        this.emotion_record_list = new_emotion_record_list;
    }
    /**
     * Mutator method. Add an EmotionRecord.
     * @param new_emotion_record the EmotionRecord to add.
     */
    public void addEmotionRecord(EmotionRecord new_emotion_record) {
        this.emotion_record_list.add(new_emotion_record);
        notifyListeners();
    }
    /**
     * Mutator method. Removes an EmotionRecord.
     * @param emotion_record_to_remove the EmotionRecord to remove.
     */
    public void removeEmotionRecord(EmotionRecord emotion_record_to_remove) {
        this.emotion_record_list.remove(emotion_record_to_remove);
        notifyListeners();
    }
    /**
     * Counts the number of times a certain Emotion has appeared in the EmotionRecord list.
     * @param emotion_to_count the kind of emotion to look for.
     * @return emotion_counter the number of times that emotion has appeared in the list.
     */
    public int countEmotionFrequency(Emotion emotion_to_count) {
        int emotion_counter = 0;
        for (int i = 0; i < this.emotion_record_list.size(); i++) {
            if (emotion_record_list.get(i).getEmotion().getEmotionName().equals(emotion_to_count.getEmotionName())) {
                emotion_counter += 1;
            }
        }
        return emotion_counter;
    }
    /**
     * Sorts the list by date.
     */
    public void sortEmotionRecordsByDate() {
        Collections.sort(this.emotion_record_list);
        notifyListeners();
    }
    /**
     * Let the listeners know that the EmotionRecord list has been changed.
     */
    private void notifyListeners() {
        for (Listener  listener : getListeners()) {
            listener.update();
        }
    }
    /**
     * Mutator method. Adds a listener.
     * @param l the Listener to add.
     */
    public void addListener(Listener l) {
        getListeners().add(l);
    }
    /**
     * Mutator method. Removes a listener.
     * @param l the Listener to remove.
     */
    public void removeListener(Listener l) {
        getListeners().remove(l);
    }
}
