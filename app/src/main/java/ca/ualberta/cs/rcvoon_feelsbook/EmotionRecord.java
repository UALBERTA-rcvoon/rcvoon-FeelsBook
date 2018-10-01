package ca.ualberta.cs.rcvoon_feelsbook;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmotionRecord implements Comparable<EmotionRecord> {

    private Date date;
    private Emotion emotion;
    private static final Integer MAX_CHARACTERS = 100;
    private String optional_comment;

    EmotionRecord() {
        this.emotion = null;
        this.date = new Date();
        this.optional_comment = "";
    }
    EmotionRecord(Emotion new_emotion, Date new_date, String new_optional_comment) {
        this.emotion = new_emotion;
        this.date = new_date;
        this.optional_comment = new_optional_comment;
    }

    public void setDate(Date new_date) {
        this.date = new_date;
    }
    public void setEmotion(Emotion new_emotion) {
        this.emotion = new_emotion;
    }
    public void setOptionalComment(String new_comment) throws CommentTooLongException {
        if (new_comment.length() <= this.MAX_CHARACTERS) {
            this.optional_comment = new_comment;
        }
        else {
            throw new CommentTooLongException();
        }
    }
    public Date getDate() {
        return this.date;
    }

    public Emotion getEmotion() {
        return this.emotion;
    }

    public String getOptional_comment() {
        return this.optional_comment;
    }

    public static int getMAX_CHARS() {return MAX_CHARACTERS;}

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String reportDate = df.format(this.date);

        return "[" + reportDate + "]     " + this.emotion.format() + "\n" + this.getOptional_comment();
    }
    @Override
    public int compareTo(EmotionRecord er) {
        return getDate().compareTo(er.getDate());
    }

}
