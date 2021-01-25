package com.hongsam.famstrory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hongsam.famstrory.data.Emotion;
import com.hongsam.famstrory.data.LetterContants;
import com.hongsam.famstrory.data.Member;

import java.util.ArrayList;

/**
 * DB 관련 함수 정의
 * 작성자 : 한재훈
 */

public class DBFamstory {
    private final String TAG = "DBFamstory";

    private static final String DB_NAME = "famstory.db";
    private static final int DB_VERSION = 1;

    private DBHelper helper;
    private SQLiteDatabase db;

    private static DBFamstory dbFamstory;

    public static DBFamstory getInstance(Context context) {
        dbFamstory = new DBFamstory(context);
        return dbFamstory;
    }

    public DBFamstory(Context context) {
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

//    /**
//     * @Description : TB_LETTERTB_LETTER 데이터들을 모두 삭제하는 함수
//     */
//    public void deleteAllLetter() {
//        db.delete(DBSchema.TB_LETTER, null, null);
//    }

    /**
     * @param letter
     * @Description : LetterContants 데이터를 insert하는 함수
    "SEQ_NO INTEGER primary key autoincrement -- 시퀀스넘버\n" +
    ",LETTER_SENDER VARCHAR(20) -- 보내는사람\n" +
    ",LETTER_CONTANTS TEXT -- 편지내용\n" +
    ",LETTER_DATE VARCHAR(20) -- 보낸날짜\n" +
    ",LETTER_PHOTO VARCHAR(100) -- 사진\n" +
    ",LETTER_PAPER_TYPE INTEGER --편지지\n" +
     */
    public void insertLetterContants(LetterContants letter) {
        if (isOpen()) {
            ContentValues values = new ContentValues();
            values.put("LETTER_SENDER", letter.getSender());
            values.put("LETTER_CONTANTS", letter.getContants());
            values.put("LETTER_DATE", letter.getDate());
            values.put("LETTER_PHOTO", letter.getPhoto());
            values.put("LETTER_PAPER_TYPE", letter.getPaperType());
            db.insert(DBSchema.TB_LETTER, null, values);
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
     * @param member
     * @Description : Member 데이터를 insert하는 함수
     * UIQUE KEY가 겹치면 insert가 아닌 update로 처리
     * SEQ_NO INTEGER primary key autoincrement -- 시퀀스넘버
     * MEMBER_RELATION VARCHAR(20) -- 관계
     * MEMBER_NAME VARCHAR(20) -- 이름
     * MEMBER_CALL VARCHAR(20) -- 호칭
     * MEMBER_TOKEN VARCHAR(100) -- 토큰
     * UNIQUE (MEMBER_RELATION,MEMBER_NAME)
     */

    public void insertMemberOnDuplicate(Member member) {
        if (isOpen()) {
            ContentValues values = new ContentValues();
            values.put("MEMBER_RELATION", member.getRelation());
            values.put("MEMBER_NAME", member.getName());
            values.put("MEMBER_CALL", member.getCall());
            values.put("MEMBER_TOKEN", member.getToken());

            db.insertWithOnConflict(DBSchema.TB_MEMBER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        } else {
            Log.d(TAG, "db null OR not open");
        }
    }

    /**
     * @Description : 전체 Member 데이터를 검색하는 함수
     * SEQ_NO INTEGER primary key autoincrement -- 시퀀스넘버
     * MEMBER_RELATION VARCHAR(20) -- 관계
     * MEMBER_NAME VARCHAR(20) -- 이름
     * MEMBER_CALL VARCHAR(20) -- 호칭
     * MEMBER_TOKEN VARCHAR(100) -- 토큰
     * UNIQUE (MEMBER_RELATION,MEMBER_NAME)
     */
    public ArrayList<Member> selectAllMemberList() {
        if (isOpen()) {
            String query = "select * from " + DBSchema.TB_MEMBER;

            ArrayList<Member> memberList = new ArrayList<>();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                Member member = new Member();
                member.setRelation(cursor.getString(1));
                member.setName(cursor.getString(2));
                member.setCall(cursor.getString(3));
                member.setToken(cursor.getString(4));

                memberList.add(member);
            }
            return memberList;
        } else {
            Log.d(TAG, "db null OR not open");
            return null;
        }
    }


    /**
     * @param relation
     * @Description : 관계로 호칭을 검색하는 함수
     * SEQ_NO INTEGER primary key autoincrement -- 시퀀스넘버
     * MEMBER_RELATION VARCHAR(20) -- 관계
     * MEMBER_NAME VARCHAR(20) -- 이름
     * MEMBER_CALL VARCHAR(20) -- 호칭
     * MEMBER_TOKEN VARCHAR(100) -- 토큰
     * UNIQUE (MEMBER_RELATION,MEMBER_NAME)
     */
    public String selectMemberCallByRelation(String relation) {
        String call = "";
        if (isOpen()) {
            String query = "select MEMBER_CALL from " + DBSchema.TB_MEMBER + " where MEMBER_RELATION = \'" + relation + "\'";
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                call = cursor.getString(0);
            }
        } else {
            Log.d(TAG, "db null OR not open");
        }
        return call;
    }

    /**
     * @param relation,call
     * @Description : 호칭을 수정하는 함수
     * SEQ_NO INTEGER primary key autoincrement -- 시퀀스넘버
     * MEMBER_RELATION VARCHAR(20) -- 관계
     * MEMBER_NAME VARCHAR(20) -- 이름
     * MEMBER_CALL VARCHAR(20) -- 호칭
     * MEMBER_TOKEN VARCHAR(100) -- 토큰
     * UNIQUE (MEMBER_RELATION,MEMBER_NAME)
     */
    public void updateMemberCall(String relation, String call) {
        if (isOpen()) {
            db.execSQL("UPDATE " + DBSchema.TB_MEMBER + " SET " +
                    "MEMBER_CALL=\'" + call + "\' WHERE MEMBER_RELATION =\'" + relation+"\'");
        } else {
            Log.d(TAG, "db null OR not open");
        }
    }
}
