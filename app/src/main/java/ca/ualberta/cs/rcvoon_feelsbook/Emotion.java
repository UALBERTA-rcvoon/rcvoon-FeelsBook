package ca.ualberta.cs.rcvoon_feelsbook;

public class Emotion {
    private String emotion_name;
    Emotion() {this.emotion_name = null;}

    Emotion(String new_emotion) {
        this.emotion_name = new_emotion;
    }

    public String getEmotionName() {
        return this.emotion_name;
    }
    public String getEmotion() {
        return this.emotion_name;
    }

}
