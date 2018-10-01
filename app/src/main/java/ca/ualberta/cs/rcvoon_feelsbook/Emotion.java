package ca.ualberta.cs.rcvoon_feelsbook;

public class Emotion {
    private String emotion;

    Emotion() {this.emotion = null;}

    Emotion(String new_emotion) {
        this.emotion = new_emotion;
    }

    public String format() {
        return this.emotion;
    }

}
