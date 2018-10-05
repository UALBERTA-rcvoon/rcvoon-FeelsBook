package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Fear</h1>
 * Fear is a subclass of emotion that returns a formatted string unique to
 * a Fear class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Fear extends Emotion {
    /**
     * Constructor.
     */
    Fear() {
        super("Fear");
    }
    /**
     *
     * formatEmotion returns a string which represents a Fear class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "----Fear----";
    }
}