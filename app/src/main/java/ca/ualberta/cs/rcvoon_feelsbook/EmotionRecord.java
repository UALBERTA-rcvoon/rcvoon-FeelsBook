package ca.ualberta.cs.rcvoon_feelsbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * <h1>EmotionRecord</h1>
 * EmotionRecord is an record of an emotion that was recorded at a certain time.
 * An EmotionRecord has a date, emotion object, and possibly a comment string which
 * can be accessed or set using the appropriate accessor or mutator methods.
 *
 * EmotionRecord implements Comparabl and overrides the compareTo method to compare
 * the date of an EmotionRecord to the date of another EmotionRecord.
 *
 * EmotionRecord overrides toString to combine the record's date, emotion, and comment
 * into a single string.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class EmotionRecord implements Comparable<EmotionRecord> {

    private Date date;
    private Emotion emotion;
    private static final Integer MAX_CHARACTERS = 100;
    private String optional_comment;
    /**
     * Constructor with no arguments.
     */
    EmotionRecord() {
        this.emotion = null;
        this.date = new Date();
        this.optional_comment = "";
    }
    /**
     * Constructor.
     * @param new_emotion the kind of emotion recorded.
     * @param new_date the
     */
    EmotionRecord(Emotion new_emotion, Date new_date, String new_optional_comment) {
        this.emotion = new_emotion;
        this.date = new_date;
        this.optional_comment = new_optional_comment;
    }
    /**
     * Mutator method
     * @param new_date date to set the EmotionRecord to
     */
    public void setDate(Date new_date) {
        this.date = new_date;
    }
    /**
     * Mutator method
     * @param new_emotion the new kind of emotion to be set.
     */
    public void setEmotion(Emotion new_emotion) {
        this.emotion = new_emotion;
    }
    /**
     * Mutator method. Throws an exception if the updated comment is more
     * than 100 characters.
     * @param new_comment the updated comment.
     */
    public void setOptionalComment(String new_comment) throws CommentTooLongException {
        if (new_comment.length() <= this.MAX_CHARACTERS) {
            this.optional_comment = new_comment;
        }
        else {
            throw new CommentTooLongException();
        }
    }
    /**
     * Accessor method
     * @return date recorded date of the EmotionRecord.
     */
    public Date getDate() {
        return this.date;
    }
    /**
     * Accessor method
     * @return emotion recorded kind of emotion of the EmotionRecord.
     */
    public Emotion getEmotion() {
        return this.emotion;
    }
    /**
     * Accessor method
     * @return optional_comment recorded comment of the EmotionRecord.
     */
    public String getOptional_comment() {
        return this.optional_comment;
    }
    /**
     * Accessor method
     * @return MAX_CHARACTERS integer value 100.
     */
    public static int getMAX_CHARS() {return MAX_CHARACTERS;}
    /**
     * Accessor method
     * @return date formatted as a ISO8601Date&Time format string.
     */
    public String getFormattedDate() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String report_date = df.format(this.getDate());
        return "[" + report_date + "] ";
    }
    /**
     * Overrides toString to combine date, emotion, and comment into a single string.
     * @return the date, emotion, and comment as a single string.
     */
    @Override
    public String toString() {
        return this.getFormattedDate() + this.getEmotion().getEmotionName() + "\n" + this.getOptional_comment();
    }
    /**
     * Overrides compareTo
     * @return 0 if the dates are equal, 1 if getDate() is greater than than er.getDate(),
     * -1 if getDate() is less than er.getDate().
     */
    @Override
    public int compareTo(EmotionRecord er) {
        return getDate().compareTo(er.getDate());
    }

}
