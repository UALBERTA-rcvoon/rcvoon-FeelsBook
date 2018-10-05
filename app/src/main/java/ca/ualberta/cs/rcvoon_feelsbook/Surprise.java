package ca.ualberta.cs.rcvoon_feelsbook;

public class Surprise extends Emotion {
    Surprise() {
        super("Surprise");
    }
    @Override
    public String format() {
        return "--Surprise--";
    }
}