package com.hongsam.famstrory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.LetterContants;

import java.util.ArrayList;

public class MyLetterDB {
    private static final String TAG = "myLetter.db";

    private static final String DB_NAME = "famstory.db";
    private static final int DB_VERSION = 1;

    private DBHelper helper;
    private SQLiteDatabase db;

    private static MyMessageDB myMessageDB;

    public static MyMessageDB getInstance(Context context) {
        return myMessageDB = new MyMessageDB(context);
    }

    public MyLetterDB(Context context) {
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
     * @param letterContants
     * @Description : letterContants 데이터를 insert하는 함수
     * SEQ_NO INTEGER primary key autoincrement 시퀀스넘버
     * LETTER_SENDER VARCHAR(20) 보내는사람
     * LETTER_MESSAGE TEXT 메세지내용
     * LETTER_SEND_DATE VARCHAR(20) 보낸시간
     * LETTER_PHOTO VARCHAR(100) 사진
     * LETTER_PAPER_TYPE INTEGER 편지지
     */
    public void insertLetter(LetterContants letterContants) {
        if (isOpen()) {
            ContentValues values = new ContentValues();
            values.put("LETTER_SENDER", letterContants.getSender());
            values.put("LETTER_MESSAGE", letterContants.getContants());
            values.put("LETTER_SEND_DATE", letterContants.getDate());
            values.put("LETTER_PHOTO", letterContants.getPhoto());
            values.put("LETTER_PAPER_TYPE", letterContants.getPaperType());
            db.insert(DBSchema.TB_LETTER, null, values);
        } else {
            Log.d(TAG, "db null OR not open");
        }
    }


    /**
     * @Description : letterContants 데이터들을 select하는 함수
     * SEQ_NO INTEGER primary key autoincrement 시퀀스넘버
     * LETTER_SENDER VARCHAR(20) 보내는사람
     * LETTER_MESSAGE TEXT 메세지내용
     * LETTER_SEND_DATE VARCHAR(20) 보낸시간
     * LETTER_PHOTO VARCHAR(100) 사진
     * LETTER_PAPER_TYPE INTEGER 편지지
     */
    public ArrayList<LetterContants> selectLetterList() {
        String query = "select * from " + DBSchema.TB_LETTER;
        ArrayList<LetterContants> letterList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            LetterContants letterContants = new LetterContants();
            letterContants.setSender(cursor.getString(1));
            letterContants.setContants(cursor.getString(2));
            letterContants.setDate(cursor.getString(3));
            letterContants.setPhoto(cursor.getString(4));
            letterContants.setPaperType(cursor.getInt(5));

            letterList.add(letterContants);
        }

        return letterList;
    }


    /**
     * @Description : TB_LETTER의 데이터들을 모두 삭제하는 함수
     */
    public void deleteAllLetter() {
        db.delete(DBSchema.TB_LETTER, null, null);
    }
}