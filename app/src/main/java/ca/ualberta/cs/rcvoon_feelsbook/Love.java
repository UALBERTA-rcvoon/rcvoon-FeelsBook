package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Love</h1>
 * Love is a subclass of emotion that returns a formatted string unique to
 * a Love class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Love extends Emotion {
    /**
     * Constructor.
     */
    Love() {
        super("Love"
        );

    }
    /**
     *
     * formatEmotion returns a string which represents a Love class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "----Love----";
    }
}