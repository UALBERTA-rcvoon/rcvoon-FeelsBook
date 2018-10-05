package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Sadness</h1>
 * Sadness is a subclass of emotion that returns a formatted string unique to
 * a Sadness class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Sadness extends Emotion {
    /**
     * Constructor.
     */
    Sadness() {
        super("Sadness");
    }
    /**
     *
     * formatEmotion returns a string which represents a Sadness class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "--Sadness--";
    }
}