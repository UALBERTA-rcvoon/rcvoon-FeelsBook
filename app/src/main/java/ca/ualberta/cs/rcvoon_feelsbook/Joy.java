package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>Joy</h1>
 * Joy is a subclass of emotion that returns a formatted string unique to
 * a Joy class object.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class Joy extends Emotion {
    /**
     * Constructor.
     */
    Joy() {
        super("Joy");
    }
    /**
     *
     * formatEmotion returns a string which represents a Joy class object.
     * Overrides formatEmotion from the Emotion class
     * @return String
     *
     */
    @Override
    public String formatEmotion() {
        return "----Joy----";
    }
}
