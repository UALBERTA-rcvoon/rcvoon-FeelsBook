package ca.ualberta.cs.rcvoon_feelsbook;

public class CommentTooLongException extends Exception {
    CommentTooLongException() {
        super("The comment is too long! Please keep your comments within 100 characters.");
    }
}
