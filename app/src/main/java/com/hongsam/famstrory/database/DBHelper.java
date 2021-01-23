package com.hongsam.famstrory.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB 생성 및 버전관리
 * 작성자 : 한재훈
 */

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBSchema.CREATE_TB_EMOTION);
        db.execSQL(DBSchema.CREATE_TB_MEMBER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTables(db);
        createTables(db);
    }

    public void createTables(SQLiteDatabase db) {
        db.execSQL(DBSchema.CREATE_TB_EMOTION);
        db.execSQL(DBSchema.CREATE_TB_MEMBER);
    }
    public void deleteTables(SQLiteDatabase db) {
        db.execSQL(DBSchema.DROP_TB_EMOTION);
        db.execSQL(DBSchema.DROP_TB_MEMBER);
    }

    public SQLiteDatabase open() throws SQLException {
        return db = getWritableDatabase();
    }

    public void close() {
        db.close();
    }
}
