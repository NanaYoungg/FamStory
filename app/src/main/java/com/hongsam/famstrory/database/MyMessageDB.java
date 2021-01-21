package com.hongsam.famstrory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hongsam.famstrory.data.Emotion;

import java.util.ArrayList;

public class MyMessageDB {
    private static final String TAG = "myMessage.db";

    private static final String DB_NAME = "famstory.db";
    private static final int DB_VERSION = 1;

    private DBHelper helper;
    private SQLiteDatabase db;

    private static MyMessageDB myMessageDB;

    public static MyMessageDB getInstance(Context context) {
        return myMessageDB = new MyMessageDB(context);
    }

    public MyMessageDB(Context context) {
        helper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        db = helper.open();
    }

    public boolean isOpen() {
        if (db == null)
            return false;
        else
            return db.isOpen();
    }

    public void close() throws Exception {
        db.close();
    }


    /**
     * @param emotion
     * @Description : Emotion 데이터를 insert하는 함수
     * SEQ_NO INTEGER primary key autoincrement 시퀀스넘버
     * EMOTION_SENDER VARCHAR(20) 보내는사람
     * EMOTION_MESSAGE VARCHAR(100)메세지내용
     * EMOTION_SEND_DATE VARCHAR(20) 보낸시간
     */
    public void insertEmotion(Emotion emotion) {
        if (isOpen()) {
            ContentValues values = new ContentValues();
            values.put("EMOTION_SENDER", emotion.getSender());
            values.put("EMOTION_MESSAGE", emotion.getMessage());
            values.put("EMOTION_SEND_DATE", emotion.getSendDate());
            db.insert(DBSchema.TB_EMOTION, null, values);
        } else {
            Log.d(TAG, "db null OR not open");
        }
    }


    /**
     * @Description : Emotion 데이터들을 select하는 함수
     * SEQ_NO INTEGER primary key autoincrement 시퀀스넘버
     * EMOTION_SENDER VARCHAR(20) 보내는사람
     * EMOTION_MESSAGE VARCHAR(100)메세지내용
     * EMOTION_SEND_DATE VARCHAR(20) 보낸시간
     */
    public ArrayList<Emotion> selectEmotionList() {
        String query = "select * from " + DBSchema.TB_EMOTION;

        ArrayList<Emotion> emotionList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Emotion emotion = new Emotion();
            emotion.setSender(cursor.getString(1));
            emotion.setMessage(cursor.getString(2));
            emotion.setSendDate(cursor.getString(3));

            emotionList.add(emotion);
        }

        return emotionList;
    }


    /**
     * @Description : TB_EMOTION의 데이터들을 모두 삭제하는 함수
     */
    public void deleteAllEmotions() {
        db.delete(DBSchema.TB_EMOTION, null, null);
    }
}