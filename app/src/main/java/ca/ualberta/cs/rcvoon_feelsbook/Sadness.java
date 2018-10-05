package ca.ualberta.cs.rcvoon_feelsbook;

public class Sadness extends Emotion {
    Sadness() {
        super("Sadness");
    }
    @Override
    public String format() {
        return "--Sadness--";
    }
}