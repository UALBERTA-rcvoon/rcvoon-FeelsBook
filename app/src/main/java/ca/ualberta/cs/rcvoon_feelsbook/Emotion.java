package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Emotion</h1>
 * Emotion objects have an emotion name (string) which is used to identify
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Emotion {
    private String emotion_name;
    /**
     * Constructor with no arguments.
     */
    Emotion() {this.emotion_name = null;}
    /**
     * Constructor.
     * @param new_emotion name of the emotion to be instantiated.
     */
    Emotion(String new_emotion) {
        this.emotion_name = new_emotion;
    }
    /**
     * Accessor method
     * @return emotion_name name of the emotion object.
     */
    public String getEmotionName() {
        return this.emotion_name;
    }
    /**
     * formatEmotion returns the name of the emotion object.
     * Usually will be overriden by subclasses which implement
     * their own formatEmotion functions.
     * @return emotion_name name of the emotion object.
     */
    public String formatEmotion() {
        return this.emotion_name;
    }

}
