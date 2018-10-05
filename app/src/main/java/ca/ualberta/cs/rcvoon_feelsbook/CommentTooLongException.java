package ca.ualberta.cs.rcvoon_feelsbook;
/**
 * <h1>CommentTooLongException</h1>
 * CommentTooLongException displays a custom exception message when a comment with greater than
 * 100 characters is inputted.
 *
 * @author  Riley Voon
 * @version 1.0
 * @since   2018-10-05
 *
 * © 2018 Riley Voon.  All rights reserved.
 *
 */
public class CommentTooLongException extends Exception {
    CommentTooLongException() {
        super("The comment is too long! Please keep your comments within 100 characters.");
    }
}
