package ca.ualberta.cs.rcvoon_feelsbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class EmotionRecordListManager {
    static final String preffile = "EmotionRecordList";
    static final String erlkey = "emotion_record_list";
    Context context;

    static private EmotionRecordListManager emotionRecordListManager = null;

    public static void initManager(Context context) {
        if (emotionRecordListManager == null) {
            if (context == null) {
                throw new RuntimeException("missing context for EmotionRecordListManager");
            }
            emotionRecordListManager = new EmotionRecordListManager(context);
        }
    }

    public static EmotionRecordListManager getManager() {
        if (emotionRecordListManager == null) {
            throw new RuntimeException("Did not initialize manager");
        }
        return emotionRecordListManager;
    }

    public EmotionRecordListManager(Context context) {
        this.context = context;
    }

    public EmotionRecordList loadEmotionRecordList() throws ClassNotFoundException, IOException {
        SharedPreferences settings = context.getSharedPreferences(preffile, Context.MODE_PRIVATE);
        String EmotionRecordListData = settings.getString(erlkey, "");
        if (EmotionRecordListData.equals("")) {
            return new EmotionRecordList();
        }
        else {
            return emotionRecordListFromString(EmotionRecordListData);
        }
    }

    static public EmotionRecordList emotionRecordListFromString(String emotion_record_list) throws ClassNotFoundException, IOException {
        ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(emotion_record_list, Base64.DEFAULT));
        ObjectInputStream oi = new ObjectInputStream(bi);
        return (EmotionRecordList) oi.readObject();

    }
    static public String emotionRecordListToString(EmotionRecordList emotion_record_list_to_string) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(emotion_record_list_to_string);
        oo.close();
        byte bytes[] = bo.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public void saveEmotionRecordList(EmotionRecordList emotion_record_list_to_save) throws IOException {
        SharedPreferences settings = context.getSharedPreferences(preffile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(erlkey, emotionRecordListToString(emotion_record_list_to_save));
        editor.commit();
    }
}
