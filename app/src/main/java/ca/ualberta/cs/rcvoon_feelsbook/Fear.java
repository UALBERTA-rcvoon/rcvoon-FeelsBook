package ca.ualberta.cs.rcvoon_feelsbook;

public class Fear extends Emotion {
    Fear() {
        super("Fear");
    }
    @Override
    public String format() {
        return "----Fear----";
    }
}