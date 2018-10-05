package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Anger</h1>
 * Anger is a subclass of emotion that returns a formatted string unique to
 * an Anger class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Anger extends Emotion {
    /**
     * Constructor.
     */
    Anger() {
        super("Anger");
    }
    /**
     *
     * formatEmotion returns a string which represents an Anger class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "---Anger---";
    }
}