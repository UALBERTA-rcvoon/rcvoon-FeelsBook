package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Surprise</h1>
 * Surprise is a subclass of emotion that returns a formatted string unique to
 * a Surprise class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Surprise extends Emotion {
    /**
     * Constructor.
     */
    Surprise() {
        super("Surprise");
    }
    /**
     *
     * Format emotion returns a string which represents a Surprise class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "--Surprise--";
    }
}